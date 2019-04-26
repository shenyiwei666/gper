package com.springframework.context;

import com.springframework.aop.config.AopConfig;
import com.springframework.aop.framework.AdvisedSupport;
import com.springframework.aop.framework.AopProxy;
import com.springframework.aop.framework.CglibAopProxy;
import com.springframework.aop.framework.JdkDynamicAopProxy;
import com.springframework.beans.BeanFactory;
import com.springframework.beans.BeanWrapper;
import com.springframework.beans.factory.config.BeanDefinition;
import com.springframework.beans.support.BeanDefinitionReader;
import com.springframework.beans.support.DefaultListableBeanFactory;
import com.springframework.stereotype.Autowired;
import com.springframework.stereotype.Controller;
import com.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shenyiwei on 2019/4/23.
 */
@Slf4j
public class ApplicationContext extends DefaultListableBeanFactory implements BeanFactory {

    private String[] configLocations;
    private BeanDefinitionReader reader;
    // 单例的IOC容器缓存
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    // 通用的IOC容器缓存
    private Map<String, Object> factoryBeanInstanceCache = new ConcurrentHashMap<>();


    public ApplicationContext(String...configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refresh() {
        // 定位配置文件
        reader = new BeanDefinitionReader(this.configLocations);

        // 加载配置文件，扫描相关类，把他们封装成BeanDefinition
        List<BeanDefinition> beanDefinitions = reader.loadBeanDefinitions();

        // 注册，把配置信息放到容器里面（伪IOC容器）
        doRegisterBeanDefinition(beanDefinitions);

        // 把不是延迟加载的类提前初始化
        doAutoWrited();


    }

    private void doAutoWrited() {
        for (Map.Entry<String, BeanDefinition> entry : super.beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            if (!entry.getValue().isLazyInit()) {
                log.info("-----------------------> getBean: " + beanName + " ---------- doAutoWrited");
                getBean(beanName);
            }
        }
    }

    private void doRegisterBeanDefinition(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
    }

    @Override
    public Object getBean(String beanNname) {
        try {
            BeanDefinition beanDefinition = super.beanDefinitionMap.get(beanNname);
            // 初始化
            BeanWrapper beanWrapper = instantiateBean(beanNname, beanDefinition);

            // 注入
            populateBean(beanNname, beanDefinition, beanWrapper);
            return  beanWrapper.getWrappedInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void populateBean(String beanNname, BeanDefinition beanDefinition, BeanWrapper beanWrapper) {
        Class<?> beanClass = beanWrapper.getWrappedClass();
        Object instance = beanWrapper.getWrappedInstance();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Class fieldClazz = field.getType();
            if (fieldClazz.isInterface()) {
                List<Class> subClassList = findSubClass(fieldClazz);
                if (subClassList.size() == 0) {
                    continue;
                }
                if (subClassList.size() > 1) {
                    throw new IllegalArgumentException(beanClass.getName() + ":" + field.getName() + ", Dependency injection failed because of multiple implementations");
                }
                fieldClazz = subClassList.get(0);
            }
            String beanName = getBeanName(fieldClazz);
            if (!singletonObjects.containsKey(beanName)) {
                throw new NullPointerException(beanClass.getName() + ":" + field.getName() + ", Dependency injection failed because of not have implementations");
            }
            field.setAccessible(true);
            try {
                field.set(instance, singletonObjects.get(beanName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
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
     * 查找接口实现类
     * @param superClass
     * @return
     */
    private List<Class> findSubClass(Class superClass) {
        List<Class> subClassList = new ArrayList<Class>();
        for (Class clazz : reader.getClassList()) {
            for (Class c : clazz.getInterfaces()) {
                if (c == superClass) {
                    subClassList.add(clazz);
                    break;
                }
            }
        }
        return subClassList;
    }

    private BeanWrapper instantiateBean(String beanName, BeanDefinition beanDefinition) {
        Object instance = null;
        if (this.singletonObjects.containsKey(beanName)) {
            instance = this.singletonObjects.get(beanName);
        } else {
            try {
                instance = beanDefinition.getBeanClass().newInstance();

                AdvisedSupport advisedSupport = instanceAopConfig(beanDefinition);
                advisedSupport.setTargetClass(beanDefinition.getBeanClass());
                advisedSupport.setTarget(instance);
                if (advisedSupport.pointCutMatch()) {
                    instance = createProxy(advisedSupport).getProxy();
                }

                log.info("-----------------------> instantiateBean: " + beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.singletonObjects.put(beanName, instance);
            this.singletonObjects.put(beanDefinition.getFactoryBeanName(), instance);
        }
        BeanWrapper beanWrapper = new BeanWrapper(instance);
        return beanWrapper;
    }

    private AopProxy createProxy(AdvisedSupport advisedSupport) {
        Class clazz = advisedSupport.getTargetClass();
        if (clazz.getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(advisedSupport);
        }
        return new CglibAopProxy(advisedSupport);
    }

    private AdvisedSupport instanceAopConfig(BeanDefinition beanDefinition) {
        AopConfig config = new AopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new AdvisedSupport(config);
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }

}
