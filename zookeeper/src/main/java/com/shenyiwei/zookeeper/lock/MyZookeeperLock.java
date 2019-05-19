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
 * zookeeper分布式锁
 * Created by shenyiwei on 2019-5-18 018.
 */
public class MyZookeeperLock {
    static String connectionUrl = "192.168.216.200:2181";
    static ZooKeeper zooKeeper;

    String lockName;    // 锁名称
    String own;     // 当前节点
    String prev;    // 上一个节点

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

    public MyZookeeperLock(String lockName) throws IOException, KeeperException, InterruptedException {
        this.lockName = lockName;
        createPersistentNode(lockName);
    }

    /**
     * 创建持久节点
     * @param node
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void createPersistentNode(String node) throws IOException, KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(node, false);
        if (stat == null) {
            synchronized (node) {
                if (stat == null) {
                    String[] nameArray = node.split("/");
                    StringBuffer sb = new StringBuffer();
                    // 多级目录，循环创建
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
        // 抢到了锁直接返回，不阻塞
        if (tryLock()) {
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  获得锁1");
            return;
        }
        CountDownLatch countDownLatch = new CountDownLatch(1);

        // 监听上一个节点
        Stat stat = zooKeeper.exists(prev, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                countDownLatch.countDown();
            }
        });

        // 上一个节点还没有释放锁，则阻塞等待锁释放
        if (stat != null) {
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  等待 " + prev + " 释放锁");
            countDownLatch.await();
//            System.out.println(Thread.currentThread().getName() + "  " + own + "  获得锁2");
        }
    }

    /**
     * 尝试获取锁
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean tryLock() throws KeeperException, InterruptedException {
        // 在锁的节点下面，创建自己对应的临时有序节点
        own = zooKeeper.create(lockName + "/", "0".getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        // 获取锁的所有子节点
        SortedSet childrenSet = getChildren(lockName);

        // 如果第一个节点是自己，表示拿到了锁
        if (childrenSet.first().equals(own)) {
            return true;
        }

        // 获取集合中自己之前的所有节点
        SortedSet<String> headSet = childrenSet.headSet(own);
        // 最后一个也就是自己的前面的那个节点
        prev = headSet.last();
        return false;
    }

    /**
     * 获取所有子节点，并按升序排序返回
     * @param path
     * @return
     */
    private SortedSet<String> getChildren(String path) {
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

    /**
     * 释放锁
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void unlock() throws KeeperException, InterruptedException {
//        System.out.println(Thread.currentThread().getName() + "  " + own + "  释放锁");
        zooKeeper.delete(own, -1);
    }

}
