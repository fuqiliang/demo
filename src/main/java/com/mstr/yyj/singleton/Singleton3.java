package com.mstr.yyj.singleton;

public class Singleton3 {
    private Singleton3(){}
    static class Holder {
        private static Singleton3 instance = new Singleton3();
    }

    public static Singleton3 getInstance(){
        return Holder.instance;
    }
}
