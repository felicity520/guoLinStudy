package com.ryd.gyy.guolinstudy.testjava;

import java.util.concurrent.locks.*;

public class LagouReentrantLockTest {

    //    创建一个ReentrantLock 锁
    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        LagouReentrantLockTest l1 = new LagouReentrantLockTest();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                l1.printLog();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                l1.printLog();
            }
        });

        t1.start();
        t2.start();
    }

    public void printLog() {
        try {
            //手动加锁
            lock.lock();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " is printing " + i);
            }
        } catch (Exception e) {
        } finally {
            //手动解锁
            //说明：在上面 ReentrantLock 的使用中，我将 unlock 操作放在 finally 代码块中。
            // 这是因为 ReentrantLock 与 synchronized 不同，当异常发生时 synchronized 会自动释放锁，
            // 但是 ReentrantLock 并不会自动释放锁。因此好的方式是将 unlock 操作放在 finally 代码块中，保证任何时候锁都能够被正常释放掉。
            lock.unlock();
        }
    }
}

