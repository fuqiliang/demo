package com.mstr.yyj.singleton;

public class Singleton2 {
    private Singleton2() {}
    private static volatile Singleton2 instance = null;
    public Singleton2 getInstance(){
        if (instance == null) {
            synchronized (Singleton2.class) {
                if (instance == null) {
                    instance = new Singleton2();
                }
            }
        }
        return instance;
    }
}
