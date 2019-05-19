package com.shenyiwei.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

/**
 * Created by shenyiwei on 2019-5-19 019.
 */
public class TestDistributeLock {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DistributeLock lock = new DistributeLock("/locks/lock1");
                        countDownLatch.await();

                        lock.lock();
                        System.out.println(Thread.currentThread().getName() + "  获得锁");

                        System.out.println(Thread.currentThread().getName() + "  释放锁");
                        lock.unlock();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDownLatch.countDown();
    }


}
