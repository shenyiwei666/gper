package com.shenyiwei.designpatterns.factorys.factorymethod.factory;

import com.shenyiwei.designpatterns.factorys.factorymethod.bean.Pad;

/**
 * Created by shenyiwei on 2019-4-30 030.
 */
public class PadFactory implements ProductFactory {
    @Override
    public Pad create() {
        return new Pad();
    }
}
