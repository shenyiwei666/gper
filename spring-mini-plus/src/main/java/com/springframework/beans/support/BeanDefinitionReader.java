package com.springframework.beans.support;

import com.springframework.beans.factory.config.BeanDefinition;
import com.springframework.stereotype.Controller;
import com.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by shenyiwei on 2019/4/23.
 */
public class BeanDefinitionReader {

    private String[] configLocations;
    // 配置文件
    private Properties config = new Properties();
    // 扫描到的所有类名
    private List<String> classNames = new ArrayList<String>();
    // 扫描到的所有类
    private List<Class> classList = new ArrayList<Class>();

    private String SCAN_PACKAGE = "componentScan";

    public BeanDefinitionReader(String[] configLocations) {
        this.configLocations = configLocations;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public List<BeanDefinition> loadBeanDefinitions() {
        loadConfig(configLocations[0]);
        scanClass(config.getProperty(SCAN_PACKAGE));

        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        for (String className : classNames) {
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(Service.class)) {
                continue;
            }
            beanDefinitions.add(doCreateBeanDefinition(clazz));
            classList.add(clazz);
        }
        return beanDefinitions;
    }

    private BeanDefinition doCreateBeanDefinition(Class clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setBeanClassName(clazz.getName());
        beanDefinition.setFactoryBeanName(getBeanName(clazz));
        return beanDefinition;
    }

    /**
     * 根据class获取beanName
     * @param clazz
     * @return
     */
    private String getBeanName(Class clazz) {
        String beanName = toLowerFirstCase(clazz.getSimpleName().trim());

        if (clazz.isAnnotationPresent(Controller.class)) {
            Controller annotation = (Controller) clazz.getAnnotation(Controller.class);
            if (!"".equals(annotation.value())) {
                beanName = annotation.value();
            }
        } else if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
            Service annotation = (Service) clazz.getAnnotation(Service.class);
            if (!"".equals(annotation.value())) {
                beanName = annotation.value();
            }
        }
        return beanName;
    }

    /**
     * 首字母大写转小写
     * @param str
     * @return
     */
    private String toLowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32; // 大写转小写
        return String.valueOf(chars);
    }

    /**
     * 扫描指定目录的class文件
     * @param scanPackage
     */
    private void scanClass(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
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

    /**
     * 加载配置文件
     * @param configLocation
     */
    private void loadConfig(String configLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configLocation);
        try {
            config.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getConfig() {
        return this.config;
    }

}
