package com.bsuresh.util;

/**
 * Created by bsuresh on 9/17/16.
 */
public class WSClient {
    public static void main(String args[]){
        System.out.println("My first gradle project!");
        new ServiceClassInvoker().invoke();
        new DynamicInvoker().invoke();
        new PlainJavaInvoker().invoke();
    }

}
