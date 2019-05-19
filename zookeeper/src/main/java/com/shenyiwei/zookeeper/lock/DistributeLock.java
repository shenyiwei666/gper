package com.shenyiwei.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

/**
 * 分布式锁
 * Created by shenyiwei on 2019-5-18 018.
 */
public class DistributeLock  {
    static String connectionUrl = "192.168.216.200:2181";
    static ZooKeeper zooKeeper;

    String lockName;
    String own;
    String prev;

    static {
        zeekeeperConnection();
    }

    private static void zeekeeperConnection() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            System.out.println(sdf.format(new Date()) + "  start connection zookeeper");
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper = new ZooKeeper(connectionUrl, 10000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    countDownLatch.countDown();
                    System.out.println("zeekeeperConnection watch");
                }
            });
            countDownLatch.await();
            System.out.println(sdf.format(new Date()) + "  connection zookeeper success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DistributeLock(String lockName) throws IOException, KeeperException, InterruptedException {
        this.lockName = lockName;
        createPersistentNode(lockName);
    }

    private void createPersistentNode(String node) throws IOException, KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(node, false);
        if (stat == null) {
            synchronized (node) {
                if (stat == null) {
                    String[] nameArray = node.split("/");
                    StringBuffer sb = new StringBuffer();
                    for (String name : nameArray) {
                        if ("".equals(name)) {
                            continue;
                        }
                        sb.append("/" + name);
                        if (zooKeeper.exists(sb.toString(), false) == null) {
                            zooKeeper.create(sb.toString(), "0".getBytes(),
                                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        }
                    }
                }
            }
        }
    }

    public void lock() throws KeeperException, InterruptedException {
        if (tryLock()) {
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  获得锁1");
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Stat stat = zooKeeper.exists(prev, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                countDownLatch.countDown();
            }
        });
        if (stat != null) {
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  等待 " + prev + " 释放锁");
            countDownLatch.await();
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  获得锁2");
        }
    }

    public boolean tryLock() throws KeeperException, InterruptedException {
        own = zooKeeper.create(lockName + "/", "0".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        SortedSet childrenSet = getChildren(lockName, false);
        if (childrenSet.first().equals(own)) {
            return true;
        }

        SortedSet<String> headSet = childrenSet.headSet(own);
        prev = headSet.last();
        return false;
    }

    private SortedSet<String> getChildren(String path, boolean watch) {
        try {
            List<String> childrenList = zooKeeper.getChildren(lockName, false);
            SortedSet childrenSet = new TreeSet();
            for (String children : childrenList) {
                childrenSet.add(lockName + "/" + children);
            }
            return childrenSet;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unlock() throws KeeperException, InterruptedException {
//        System.out.println(Thread.currentThread().getName() + "  " + own + "  释放锁");
        zooKeeper.delete(own, -1);
    }

}
