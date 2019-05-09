package com.shenyiwei.designpatterns.observers.guavaobserver;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.EventBus;

import java.util.List;

/**
 * Created by shenyiwei on 2019-5-9 009.
 */
public class Gper {
    EventBus eventBus = new EventBus();

    public void publishQuestion(Question question, List<Teacher> teacherList) {
        System.out.println(question.getCreator() + "在Gper社区提交了一个问题。");

        if (teacherList != null) {
            for (Teacher teacher : teacherList) {
                eventBus.register(teacher);
            }
        }
        // 对象类型Teacher订阅收不到，不知道为什么
//        eventBus.post(question);
        eventBus.post(JSONObject.toJSONString(question));
    }

}
