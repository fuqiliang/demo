package com.mstr.yyj;

import java.util.concurrent.Semaphore;

public class PrintInOrder {

    private Semaphore semaphoreOne = new Semaphore(0);
    private Semaphore semaphoreTwo = new Semaphore(0);

    public PrintInOrder(){
    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        System.out.println("first");
        semaphoreOne.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        semaphoreOne.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        System.out.println("second");
        semaphoreTwo.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        semaphoreTwo.acquire();
        // printThird.rusn() outputs "third". Do not change or remove this line.
        System.out.println("third");
    }
}
