package com.springframework.webmvc.servlet;

import com.springframework.context.ApplicationContext;
import com.springframework.stereotype.Controller;
import com.springframework.stereotype.RequestMapping;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenyiwei on 2019/4/15.
 */
public class DispatchServlet extends HttpServlet {
    String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    ApplicationContext applicationContext;

    private List<HandlerMapping> handlerMappings = new ArrayList<>();

    private Map<HandlerMapping, HandlerAdapter> handerAdapters = new HashMap<>();

    private List<ViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        HandlerMapping hm = getHandler(req);
        if (hm == null) {
            processDispatchResult(req, resp, new ModelAndView("/404"));
            return;
        }
        HandlerAdapter ha = getHandlerAdapter(hm);
        try {
            Object result = ha.handle(req, resp, hm);
            if (result instanceof ModelAndView) {
                processDispatchResult(req, resp, (ModelAndView) result);
            } else {
                resp.getWriter().write((String) result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, ModelAndView mv) {
        if (mv == null) { return; }
        if (this.viewResolvers.isEmpty()) { return; }
        for (ViewResolver viewResolver : this.viewResolvers) {
            if (viewResolver != null) {
                View view = viewResolver.resolveViewName(mv.getViewName(), null);
                view.render(mv.getModel(), req, resp);
                return;
            }
        }
    }

    private HandlerAdapter getHandlerAdapter(HandlerMapping handler) {
        if (this.handerAdapters.isEmpty()) { return null; }
        HandlerAdapter ha = this.handerAdapters.get(handler);
        if (ha.supports(handler)) {
            return ha;
        }
        return null;
    }

    private HandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()) { return null; }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            Matcher matcher = handlerMapping.getPattern().matcher(url);
            if (!matcher.matches()) { continue; }
            return handlerMapping;
        }
        return null;
    }



    @Override
    public void init(ServletConfig config) {
        // 初始化上下文
        applicationContext = new ApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        // 初始化九大组件
        initStrategies(applicationContext);

    }

    //初始化策略
    protected void initStrategies(ApplicationContext context) {
        //多文件上传的组件
        initMultipartResolver(context);
        //初始化本地语言环境
        initLocaleResolver(context);
        //初始化模板处理器
        initThemeResolver(context);
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化异常拦截器
        initHandlerExceptionResolvers(context);
        //初始化视图预处理器
        initRequestToViewNameTranslator(context);
        //初始化视图转换器
        initViewResolvers(context);
        //
        initFlashMapManager(context);
    }

    private void initFlashMapManager(ApplicationContext context) {

    }

    private void initViewResolvers(ApplicationContext context) {
        String templateRoot = this.applicationContext.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource("").getPath().replace("/classes/", "");
        templateRootPath = templateRootPath.substring(1) + templateRoot;
        File templateRootDir = new File(templateRootPath);
        for (File file : templateRootDir.listFiles()) {
            this.viewResolvers.add(new ViewResolver(templateRootDir));
        }
    }

    private void initRequestToViewNameTranslator(ApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(ApplicationContext context) {

    }

    private void initHandlerAdapters(ApplicationContext context) {
        for (HandlerMapping handlerMapping : this.handlerMappings) {
            this.handerAdapters.put(handlerMapping, new HandlerAdapter());
        }

    }

    private void initHandlerMappings(ApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            System.out.println("initHandlerMappings----------> getBean--" + beanName);
            Object bean = context.getBean(beanName);
            Class clazz = bean.getClass();

            if (!clazz.isAnnotationPresent(Controller.class) && !clazz.isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            RequestMapping classAnnotation = bean.getClass().getAnnotation(RequestMapping.class);
            String baseUrl = classAnnotation.value();

            for (Method method : bean.getClass().getMethods()) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                String regex = ("/" + baseUrl + "/" + methodAnnotation.value().replaceAll("\\*", ".")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                handlerMappings.add(new HandlerMapping(bean, method, pattern));
            }

        }
    }

    private void initThemeResolver(ApplicationContext context) {

    }

    private void initLocaleResolver(ApplicationContext context) {

    }

    private void initMultipartResolver(ApplicationContext context) {

    }

}
