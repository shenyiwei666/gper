package com.shenyiwei.designpatterns.observers.jdkobserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class Teacher implements Observer {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Override
    public void update(Observable o, Object arg) {
        Gper gper = (Gper) o;
        Question question = (Question) arg;
        System.out.println("=========================================");
        StringBuffer sb = new StringBuffer();
        sb.append(name + "老师，您好！\n");
        sb.append("您收到一个来自Gper社区的提问，希望您能解答，问题内容如下：\n");
        sb.append(question.getContent() + "\n");
        sb.append("提问者：" + question.getCreator());
        System.out.println(sb.toString());
    }
}
