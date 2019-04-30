package com.shenyiwei.designpatterns.factory.factorymethod.factory;

import com.shenyiwei.designpatterns.factory.factorymethod.bean.Pad;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class PadFactory implements ProductFactory {
    @Override
    public Pad create() {
        return new Pad();
    }
}
