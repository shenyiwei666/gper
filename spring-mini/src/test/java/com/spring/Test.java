package com.spring;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shenyiwei on 2019/4/15.
 */
public class Test {

    private static List<String> classNames = new ArrayList<String>();

    public static void main(String[] args) {
        scanClass("com.demo");
        System.out.println(classNames);

        try {
            findSubClass(Class.forName("com.demo.service.UserService"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static List<Class> findSubClass(Class superClass) {
        List<Class> subClassList = new ArrayList<Class>();
        for (String className : classNames) {
            Class subClass = null;
            try {
                subClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            for (Class interfaces : subClass.getInterfaces()) {
                if (interfaces == superClass) {
                    subClassList.add(subClass);
                    break;
                }
            }
        }
        return subClassList;
    }

    /**
     * 扫描指定目录的class文件
     * @param scanPackage
     */
    private static void scanClass(String scanPackage) {
        URL url = Test.class.getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        for (File f : file.listFiles()) {
            String classFileName = scanPackage + "." + f.getName();
            if (f.isDirectory()) {
                scanClass(classFileName);
            } else {
                if (!f.getName().endsWith(".class")) {
                    continue;
                }
                String className = classFileName.replace(".class", "");
                classNames.add(className);
            }
        }

    }

}
