package com.shenyiwei.designpatterns.proxys.myproxy.core;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class MyClassLoader extends ClassLoader {
    private File classPathFile;

    public MyClassLoader() {
        String classPath = MyClassLoader.class.getResource("").getPath();
        classPathFile = new File(classPath);
    }

    public Class getClass(String className) {
        if (classPathFile == null) {
            return null;
        }
        String classFilePath = className.replaceAll("\\.", "/") + ".class";
        File classFile = new File(classPathFile, classFilePath);
        if (!classFile.exists()) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(classFile);
            bos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = fis.read(buff)) != -1) {
                bos.write(buff, 0, len);
            }
            String name = MyClassLoader.class.getPackage().getName() + "." + className;
            return defineClass(name, bos.toByteArray(), 0, bos.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
