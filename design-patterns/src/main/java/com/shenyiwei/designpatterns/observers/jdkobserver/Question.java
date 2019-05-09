package com.shenyiwei.designpatterns.observers.jdkobserver;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class Question {
    // 提问内容
    private String content;
    // 提问者
    private String creator;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
