package com.shenyiwei.datastructure.linearlist.uniderection;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 单向链表
 * Created by shenyiwei on 2019/4/25.
 */
public class SinglyLinkedList {
    // 头部节点
    private Node headNode;
    // 尾部节点
    private Node tailNode;
    // 总结点数
    private AtomicLong size = new AtomicLong(0);

    public long getSize() {
        return this.size.get();
    }

    public void add(Object value) {
        Node node = new Node(value);
        if (getSize() == 0) {
            tailNode = node;
            headNode = node;
        } else if (getSize() == 1) {
            headNode = tailNode;
            tailNode = node;
            headNode.setNext(tailNode);
        } else {
            Node oldTailNode = tailNode;
            tailNode = node;
            oldTailNode.setNext(tailNode);
        }
        size.incrementAndGet();
        System.out.println(headNode + "---" + tailNode);
    }

    public boolean delete(Object value) {
        Node headNode = this.headNode;
        if (value == null || "".equals(value)) { return false; }
        if (headNode.getValue().equals(value)) {
            this.headNode = this.headNode.getNext();
            headNode = null;
            size.decrementAndGet();
            return true;
        }

        while (headNode != null) {
            Node nextNode = headNode.getNext();
            if (nextNode.getValue().equals(value)) {
                Node newNextNode = nextNode.getNext();
                headNode.setNext(newNextNode);
                nextNode = null;
                size.decrementAndGet();
                return true;
            }
            headNode = headNode.getNext();
        }
        return false;
    }

    public void print() {
        Node headNode = this.headNode;
        while (headNode != null) {
            System.out.print(headNode.getValue() + " ");
            headNode = headNode.getNext();
        }
        System.out.println();
    }

    class Node {
        // 节点值
        private Object value;
        // 后继指针
        private Node next;

        public Node(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

}
