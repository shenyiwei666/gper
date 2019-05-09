package com.shenyiwei.designpatterns.observers.jdkobserver;

import java.util.Arrays;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class TestJdkObserver {

    public static void main(String[] args) {
        Question question = new Question();
        question.setContent("装饰者模式和适配器模式有什么区别？");
        question.setCreator("易水寒");

        Teacher tom = new Teacher("Tom");
        Teacher mic = new Teacher("Mic");

        Gper gper = new Gper();
        gper.publishQuestion(question, Arrays.asList(tom, mic));
    }

}
