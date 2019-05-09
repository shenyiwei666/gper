package com.shenyiwei.designpatterns.observers.jdkobserver;

import java.util.List;
import java.util.Observable;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class Gper extends Observable {

    public void publishQuestion(Question question, List<Teacher> teacherList) {
        System.out.println(question.getCreator() + "在Gper社区提交了一个问题。");

        if (teacherList != null) {
            for (Teacher teacher : teacherList) {
                addObserver(teacher);
            }
        }

        setChanged();
        notifyObservers(question);
    }

}
