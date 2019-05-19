package com.shenyiwei.zookeeper.demo;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用curator框架监听
 * Created by shenyiwei on 2019-5-17 017.
 */
public class CuratorWatch {

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
//                .connectString("192.168.216.101:2181,192.168.216.102:2181,192.168.216.103:2181")
                .connectString("192.168.216.200:2181")
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .namespace("syw")
                .build();

        curatorFramework.start();

        String path = "/n3";

        // 当前节点的创建、删除事件监听
//        addListenerWithNodeCache(curatorFramework, path);

        // 子节点的创建、修改、删除事件监听
//        addListenerWithPathChildCache(curatorFramework, path);

        // 综合节点（当前节点、子节点）的事件监听
        addListenerWithTreeCache(curatorFramework, path);

        System.in.read();
    }

    /**
     * 综合节点（当前节点、子节点）的事件监听
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    private static void addListenerWithTreeCache(CuratorFramework curatorFramework, String path) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework, path);
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                System.out.println(event.getType() + " --> " + event.getData().getPath());
            }
        };
        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }

    /**
     * 子节点的创建、修改、删除事件监听
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    private static void addListenerWithPathChildCache(CuratorFramework curatorFramework, String path) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println(event.getType() + " --> " + event.getData().getPath());
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);
    }

    /**
     * 当前节点的创建、删除事件监听
     * @param curatorFramework
     * @param path
     * @throws Exception
     */
    private static void addListenerWithNodeCache(CuratorFramework curatorFramework, String path) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework, path);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("Receive event: " + nodeCache.getCurrentData().getPath());
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

}
