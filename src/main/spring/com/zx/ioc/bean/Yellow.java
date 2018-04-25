package com.zx.ioc.bean;

public class Yellow {

    public Yellow() {
        System.out.println("yellow constructors....");
    }

    public void init() {
        System.out.println("yellow init .....");
    }
    public void destroy() {
        System.out.println("yellow destroy ....");
    }
}
