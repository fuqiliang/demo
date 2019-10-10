package com.mstr.yyj.producerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProAndCon {

    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(3);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        consumer.start();
        producer.start();

    }

    static abstract class QueueThread implements Runnable {
        protected final BlockingQueue<String> queue;
        private volatile boolean stop = false;
        private volatile Thread thread = null;

        public QueueThread(BlockingQueue queue) {
            this.queue = queue;
        }

        public synchronized void start() {
            if (thread != null) return;

            thread = new Thread(this);
//            thread.setDaemon(true);
            thread.start();
        }

        public synchronized void stop() {
            stop = true;
        }

        abstract void run0() throws InterruptedException;

        @Override
        public void run() {
            try {
                while (!stop && !thread.isInterrupted()) {
                   run0();
                }
            } catch (InterruptedException e) {
                System.out.println("======= thread interrupted, consumer exit =======");
            }
        }
    }

    static class Consumer extends QueueThread {

        private AtomicInteger count = new AtomicInteger(0);

        public Consumer(BlockingQueue queue) {
            super(queue);
        }

        @Override
        public void run0() throws InterruptedException {
            String msg = count.getAndIncrement() + "";
            queue.put(msg);
            System.out.println("Put msg:" + msg);
            TimeUnit.SECONDS.sleep(3);
        }
    }

    static class Producer extends QueueThread {
        public Producer(BlockingQueue queue) {
            super(queue);
        }

        @Override
        public void run0() throws InterruptedException {
            TimeUnit.SECONDS.sleep(3);
            String msg = queue.take();
            System.out.println("Got msg:" + msg);
        }
    }
}
