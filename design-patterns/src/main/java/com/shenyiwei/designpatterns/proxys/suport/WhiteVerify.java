package com.shenyiwei.designpatterns.proxys.suport;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class WhiteVerify {
    static ThreadLocal<Boolean> threadLocal = new ThreadLocal();

    public static boolean isWhite() {
        if (threadLocal.get() == null) {
            return false;
        }
        return threadLocal.get();
    }

    public static void setWhite(boolean isWhite) {
        threadLocal.set(isWhite);
    }

}
