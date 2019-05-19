package com.shenyiwei.zookeeper.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

/**
 * 使用curator框架操作
 * Created by shenyiwei on 2019-5-17 017.
 */
public class CuratorOpeate {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
//                .connectString("192.168.216.101:2181,192.168.216.102:2181,192.168.216.103:2181")
                .connectString("192.168.216.200:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("syw")
                .build();

        curatorFramework.start();

        String path = "/n1";

//        curatorFramework.create().creatingParentsIfNeeded()
//                .withMode(CreateMode.PERSISTENT)
//                .forPath(path, "1".getBytes());

//        curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);

        Stat stat = new Stat();

        System.out.println(new String(curatorFramework.getData().storingStatIn(stat).forPath(path)));
        System.out.println("get --> " + JSONObject.toJSONString(stat));

        curatorFramework.setData().withVersion(stat.getVersion()).forPath(path, "3".getBytes());
        System.out.println("set --> " + JSONObject.toJSONString(stat));

        System.out.println(new String(curatorFramework.getData().storingStatIn(stat).forPath(path)));
        System.out.println("get --> " + JSONObject.toJSONString(stat));

        curatorFramework.setData().withVersion(stat.getVersion()).forPath(path, "4".getBytes());
        System.out.println("set --> " + JSONObject.toJSONString(stat));

    }

}
