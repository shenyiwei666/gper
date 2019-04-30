package com.shenyiwei.datastructure.linearlist.uniderection;

/**
 * Created by shenyiwei on 2019/4/25.
 */
public class Test {

    public static void main(String[] args) {
        SinglyLinkedList list = new SinglyLinkedList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("6");
        list.add("8");

        list.print();
        System.out.println(list.getSize());
        list.delete("1");
        list.delete("6");
        System.out.println(list.getSize());
        list.print();
    }

}
