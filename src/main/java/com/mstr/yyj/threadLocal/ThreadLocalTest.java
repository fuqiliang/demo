package com.mstr.yyj.threadLocal;

import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void main(String[] args){
        for (int i = 0; i < 4; i++) {
            new Thread(new TestRunnable("msg" + i)).start();
        }
    }

    static class TestRunnable implements Runnable {

        private final String id;

        TestRunnable(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            threadLocal.set(this.id);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread-"+ id +",threadLocal:" + threadLocal.hashCode() + ", msg:" + threadLocal.get());
        }
    }

}
