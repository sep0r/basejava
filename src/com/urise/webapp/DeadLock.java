package com.urise.webapp;

public class DeadLock {
    static int counter;

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.Thread1();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                runner.Thread2();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }

    public static void count() {
        counter++;
    }
}

class Runner {
    DeadLock deadLock = new DeadLock();
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void Thread1() {
        for (int i = 0; i < 10000; i++) {
            synchronized (lock1) {
                synchronized (lock2) {
                    DeadLock.count();
                }
            }
        }
    }

    public void Thread2() {
        for (int i = 0; i < 10000; i++) {
            synchronized (lock2) {
                synchronized (lock1) {
                    DeadLock.count();
                }
            }
        }
    }
}
