package com.bsuresh.util;

import com.bsuresh.util.wsclient.Weather;
import com.bsuresh.util.wsclient.WeatherSoap;

import javax.xml.namespace.QName;

public class ServiceClassInvoker implements ServiceInvoker {
    @Override
    public void invoke() {
        QName qname = new QName("http://ws.cdyne.com/WeatherWS/", "Weather");
        Weather service = new Weather(null,qname);
        WeatherSoap port = service.getWeatherSoap12();
        //BindingProvider bindingProvider = (BindingProvider) port;
        //bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
        System.out.println(port.getCityWeatherByZIP("60060").getTemperature());

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