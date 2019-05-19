package com.shenyiwei.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * Created by shenyiwei on 2019-5-19 019.
 */
public class TestCuratorZookeeperLock {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                            .connectString("192.168.216.200:2181")
                            .sessionTimeoutMs(5000)
                            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                            .build();

                    curatorFramework.start();

                    InterProcessMutex mutex = new InterProcessMutex(curatorFramework,
                            "/locks/lock2");

                    try {
                        countDownLatch.await();

                        mutex.acquire();
                        System.out.println(Thread.currentThread().getName() + "  获得锁");

                        mutex.release();
                        System.out.println(Thread.currentThread().getName() + "  释放锁");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        countDownLatch.countDown();
    }

}
