package com.bsuresh.util;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bsuresh on 9/18/16.
 */
public class PlainJavaInvoker implements ServiceInvoker {
    @Override
    public void invoke() {


        try
        {
            URL url = new URL("http://wsf.cdyne.com/WeatherWS/Weather.asmx");
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            String xmlInput =

                    "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:weat=\"http://ws.cdyne.com/WeatherWS/\">"+
                       "<soap:Header/>"+
                       "<soap:Body>"+
                          "<weat:GetCityForecastByZIP>"+
                             "<!--Optional:-->"+
                             "<weat:ZIP>60060</weat:ZIP>"+
                          "</weat:GetCityForecastByZIP>"+
                       "</soap:Body>"+
                    "</soap:Envelope>";


            byte[] buffer = new byte[xmlInput.length()];
            buffer = xmlInput.getBytes();
            bout.write(buffer);
            byte[] b = bout.toByteArray();
            String SOAPAction =
                    "http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP";
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Length",
                    String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setConnectTimeout(99999999);
            httpConn.setReadTimeout(99999999);
            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();

            //Read the response
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String responseString = "";
            String outputString = "";
            //Move the SOAP message response to a String.
            while ((responseString = in.readLine()) != null) {
                outputString = outputString + responseString;
            }

            //Parse the String output to a org.w3c.dom.Document and be able to reach every node with the org.w3c.dom API
            Document document = parseXml(outputString);
            NodeList nodeLst = document.getElementsByTagName("DaytimeHigh");
            String weatherResult = nodeLst.item(0).getTextContent();
            System.out.println("Weather of the city: " + weatherResult);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Document parseXml(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
