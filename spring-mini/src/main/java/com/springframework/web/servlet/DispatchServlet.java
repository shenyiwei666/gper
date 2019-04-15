package com.springframework.web.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by shenyiwei on 2019/4/15.
 */
public class DispatchServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> beanClassNames = new ArrayList<String>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("***********************doDispatch, URL = " + req.getRequestURL());
        try {
            resp.getWriter().write(new Timestamp(System.currentTimeMillis()) + "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("***********************init");
        // 加载配置文件
        loadConfig(config.getInitParameter("contextConfigLocation"));

        // 扫描要托管的bean
        scanBean(contextConfig.getProperty("bean.scan.package"));

        // 创建托管bean放入ioc容器
        createBean();

        // 注入bean中的依赖
        initBean();

        // 初始化handlerMapping
        initHandlerMapping();

    }

    private void initHandlerMapping() {
    }

    private void initBean() {
    }

    private void createBean() {
    }

    private void scanBean(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", File.separator));
        File file = new File(url.getFile());
        for (File f : file.listFiles()) {
            String classFileName = scanPackage + "." + f.getName();
            if (f.isDirectory()) {
                scanBean(classFileName);
            } else {
                if (!f.getName().endsWith(".class")) {
                    continue;
                }
                String className = classFileName.replace(".class", "");
                beanClassNames.add(className);
            }
        }

    }

    private void loadConfig(String configLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configLocation);
        try {
            contextConfig.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
