package com.mstr.yyj.deadlock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    public static void main(String[] args) {
        String a = new String("a");
        String b = new String("b");

        Thread t1 = new Thread(new TestRun(a, b));
        Thread t2 = new Thread(new TestRun(b, a));

        t1.start();
        t2.start();

    }

    static class TestRun implements Runnable {

        private String firstLock;
        private String secondLock;

        public TestRun(String firstLock, String secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @Override
        public void run() {
            synchronized (firstLock){
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("======get lock" + firstLock + "============");
                synchronized (secondLock){
                    System.out.println("======get lock" + secondLock + "============");
                }
            }
        }
    }
}
