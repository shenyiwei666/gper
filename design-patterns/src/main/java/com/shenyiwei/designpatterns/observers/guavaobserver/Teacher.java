package com.shenyiwei.designpatterns.observers.guavaobserver;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.shenyiwei.designpatterns.observers.jdkobserver.Question;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    @Subscribe
    public void subscribe(String content) {
        Question question = JSONObject.parseObject(content, Question.class);
        subscribe(question);
    }

    @Subscribe
    public void subscribe(Question question) {
        System.out.println("=========================================");
        StringBuffer sb = new StringBuffer();
        sb.append(name + "老师，您好！\n");
        sb.append("您收到一个来自Gper社区的提问，希望您能解答，问题内容如下：\n");
        sb.append(question.getContent() + "\n");
        sb.append("提问者：" + question.getCreator());
        System.out.println(sb.toString());
    }

}
