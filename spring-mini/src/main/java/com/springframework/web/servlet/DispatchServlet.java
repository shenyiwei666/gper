package com.springframework.web.servlet;

import com.springframework.stereotype.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;

/**
 * Created by shenyiwei on 2019/4/15.
 */
public class DispatchServlet extends HttpServlet {
    // 配置文件
    private Properties contextConfig = new Properties();
    // 扫描到的所有类名
    private List<String> classNames = new ArrayList<String>();
    // 扫描到的所有类
    private List<Class> classList = new ArrayList<Class>();
    // ioc容器
    private Map<String, Object> ioc = new HashMap<String, Object>();
    // 请求url和方法的映射
    private Map<String, Method> handlerMapping = new HashMap<String, Method>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("*********************** doDispatch, URL = " + req.getRequestURL());
        PrintWriter writer = null;
        try {
            writer = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();
        uri = uri.replaceAll(contextPath, "").replaceAll("/+", "/");
        if (!handlerMapping.containsKey(uri)) {
            outputPage(writer, "404.html");
            return;
        }
        Method method = handlerMapping.get(uri);
        String beanName = getBeanName(method.getDeclaringClass());

        Parameter[] parameters = method.getParameters();
        Object[] paramValues = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            Class clazz = param.getClass();
            Annotation[] annotations = param.getAnnotations();
            if (clazz == HttpServletRequest.class) {
                paramValues[i] = req;
            } else if (clazz == HttpServletResponse.class) {
                paramValues[i] = resp;
            } else {
                String paramName = null;
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType() == RequestParam.class) {
                        RequestParam requestParam = (RequestParam) annotation;
                        paramName = requestParam.value();
                        break;
                    }
                }

                if (paramName != null) {
                    try {
                        paramValues[i] = convert(req.getParameter(paramName), param.getType());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        writer.write(e.getMessage());
                        return;
                    }
                } else {
                    paramValues[i] = null;
                }
            }
        }

        try {
            Object result = method.invoke(ioc.get(beanName), paramValues);
            if (result != null && result != Void.class) {
                writer.write((String) result);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void outputPage(PrintWriter writer, String pagePath) {
        String path = this.getClass().getClassLoader().getResource("").getPath().replace("/classes/", "");
        path = path.substring(1) + "/page/" + pagePath;
        File file = new File(path);
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(content.toString());
    }

    private Object convert(String value, Class type) throws IllegalAccessException {
        if (type == String.class) {
            return value;
        } else if (type == Integer.class) {
            return Integer.parseInt(value);
        } else if (type == Double.class) {
            return Double.parseDouble(value);
        } else if (type == Float.class) {
            return Float.parseFloat(value);
        } else if (type == Long.class) {
            return Long.parseLong(value);
        } else {
            throw new IllegalAccessException(" Data type is not supported, " + value);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            // 加载配置文件
            loadConfig(config.getInitParameter("contextConfigLocation"));
            // 扫描class文件
            scanClass(contextConfig.getProperty("componentScan"));
            // 初始化ioc容器
            initIoc();
            // 依赖注入
            dependencyInjection();
            // 初始化handlerMapping
            initHandlerMapping();

            System.out.println("*********************** init");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void initHandlerMapping() {
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object bean = entry.getValue();
            if (!bean.getClass().isAnnotationPresent(RequestMapping.class)) {
                continue;
            }
            RequestMapping classAnnotation = bean.getClass().getAnnotation(RequestMapping.class);
            String baseUrl = "/" + classAnnotation.value();

            for (Method method : bean.getClass().getMethods()) {
                if (!method.isAnnotationPresent(RequestMapping.class)) {
                    continue;
                }
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                String url = (baseUrl + "/" + methodAnnotation.value()).replaceAll("/+", "/");
                handlerMapping.put(url, method);
            }
        }
    }

    /**
     * 依赖注入
     */
    private void dependencyInjection() throws ClassNotFoundException, IllegalAccessException {
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {
            Object bean = entry.getValue();
            Field[] fields = bean.getClass().getFields();
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Autowired.class)) {
                    continue;
                }
                Class clazz = field.getType();
                if (clazz.isInterface()) {
                    List<Class> subClassList = findSubClass(clazz);
                    if (subClassList.size() == 0) {
                        continue;
                    }
                    if (subClassList.size() > 1) {
                        throw new IllegalArgumentException(bean.getClass().getName() + ":" + field.getName() + ", Dependency injection failed because of multiple implementations");
                    }
                    clazz = subClassList.get(0);
                }
                String beanName = getBeanName(clazz);
                if (!ioc.containsKey(beanName)) {
                    throw new NullPointerException(bean.getClass().getName() + ":" + field.getName() + ", Dependency injection failed because of not have implementations");
                }
                field.set(bean, ioc.get(beanName));
            }
        }
    }

    /**
     * 初始化ioc容器
     */
    private void initIoc() throws ServletException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String className : classNames) {
            Class<?> clazz = Class.forName(className);
            classList.add(clazz);
            if (clazz.isAnnotationPresent(Controller.class) || clazz.isAnnotationPresent(Service.class)) {
                String beanName = getBeanName(clazz);
                ioc.put(beanName, clazz.newInstance());
            }
        }
    }

    /**
     * 查找接口实现类
     * @param superClass
     * @return
     */
    private List<Class> findSubClass(Class superClass) throws ClassNotFoundException {
        List<Class> subClassList = new ArrayList<Class>();
        for (Class clazz : classList) {
            for (Class c : clazz.getInterfaces()) {
                if (c == superClass) {
                    subClassList.add(clazz);
                    break;
                }
            }
        }
        return subClassList;
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
    private void loadConfig(String configLocation) throws IOException {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(configLocation);
        contextConfig.load(resourceAsStream);
    }
}
