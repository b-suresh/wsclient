package com.bsuresh.util;

import javax.xml.namespace.QName;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * Created by bsuresh on 9/20/16.
 */
public class DynamicInvoker implements ServiceInvoker {
        @Override
        public void invoke() {
            QName qname = new QName("http://ws.cdyne.com/WeatherWS/", "Weather");
            try {
                Class cl = Class.forName("com.bsuresh.util.wsclient.Weather");
                Constructor cons = cl.getConstructor(URL.class, QName.class);
                Object obj1 = cons.newInstance(null,qname);
                Object obj2 = cl.getMethod("getWeatherSoap12").invoke(obj1);
                Object obj3 = obj2.getClass().getMethod("getCityWeatherByZIP",String.class).invoke(obj2,"60060");
                Object objResult = obj3.getClass().getMethod("getTemperature").invoke(obj3);
                System.out.println("The temperature is "+objResult);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    /*private Object<T1,T2> invoke(String className, T1 classParam, Method methodName, T2 methodParam)
            throws NoSuchMethodException, ClassNotFoundException,
                    InvocationTargetException, IllegalAccessException {
        Class cl = Class.forName(className);
        Constructor cons = cl.getConstructor(URL.class, QName.class);
        Object obj1 = cons.newInstance(null,qname);
        Object obj2 = cl.getMethod("getWeatherSoap12").invoke(obj1);
        return obj2;
    }*/
}
