package com.shenyiwei.zookeeper.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 使用zookeeper原生api操作
 * Created by shenyiwei on 2019-5-16 016.
 */
public class ZookeeperOperate {

    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("192.168.216.200:2181",
                    50000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    countDownLatch.countDown();
                    System.out.println("default watche --> " + JSONObject.toJSONString(watchedEvent));
                }
            });

            countDownLatch.await();

            System.out.println(zooKeeper.getState());

            String path = "/syw/n1";

            // 绑定监听事件
            Stat stat = zooKeeper.exists(path, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("watche --> " + JSONObject.toJSONString(watchedEvent));
                    try {
                        zooKeeper.exists(path, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("exists --> " + JSONObject.toJSONString(stat));

//            zooKeeper.create(path, "1".getBytes(),
//                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

//            System.out.println(new String(zooKeeper.getData(path, null, stat)));
            System.out.println("get --> " + JSONObject.toJSONString(stat));

            zooKeeper.setData(path, "1".getBytes(), stat.getVersion());
            System.out.println("set --> " + JSONObject.toJSONString(stat));

//            System.out.println(new String(zooKeeper.getData(path, null, stat)));
            System.out.println("get --> " + JSONObject.toJSONString(stat));

            zooKeeper.setData(path, "2".getBytes(), stat.getVersion());
            System.out.println("set --> " + JSONObject.toJSONString(stat));

//            zooKeeper.delete(path, stat.getVersion());
//            System.out.println("del --> " + JSONObject.toJSONString(stat));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
