package com.shenyiwei.designpatterns.proxys.myproxy.core;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenyiwei on 2019-5-7 007.
 */
public class MyProxy {

    public static Object newProxyInstance(MyClassLoader classLoader,
                                          Class<?>[] interfaces,
                                          MyInvocationHandler h) {
        try {
            Class clazz = create$Proxy0Class(classLoader, interfaces);
            Constructor constructor = clazz.getConstructor(MyInvocationHandler.class);
            Object instance = constructor.newInstance(h);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Class create$Proxy0Class(MyClassLoader classLoader, Class[] interfaces) throws IOException {
        // 生成java源文件
        String src = create$Proxy0Java(interfaces);

        // 把java源文件写入磁盘
        String filePath = MyProxy.class.getResource("").getPath();
        File file = new File(filePath + "$Proxy0.java");
        FileWriter fw = new FileWriter(file);
        fw.write(src);
        fw.flush();
        fw.close();

        // 把生成的java文件编译成class文件
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable fileObjects = manager.getJavaFileObjects(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, fileObjects);
        task.call();
        manager.close();

        // 加载class文件到JVM中
        Class clazz = classLoader.getClass("$Proxy0");
        file.delete();
        return clazz;
    }

    /**
     * 生成java源文件
     * @param interfaces
     * @return
     */
    private static String create$Proxy0Java(Class[] interfaces) {
        String n = "\n";
        StringBuffer sb = new StringBuffer();
        sb.append("package com.shenyiwei.designpatterns.proxys.myproxy.core;" + n);
        sb.append("import java.lang.reflect.*;" + n);
        sb.append("public class $Proxy0 implements "+ interfaces[0].getName() +" {" + n);
            sb.append("MyInvocationHandler h;" + n);
            sb.append("public $Proxy0(MyInvocationHandler h) {" + n);
                sb.append("this.h = h;" + n);
            sb.append("}" + n);
            for (Method m : interfaces[0].getMethods()){
                Class<?>[] params = m.getParameterTypes();

                StringBuffer paramNames = new StringBuffer();
                StringBuffer paramValues = new StringBuffer();
                StringBuffer paramClasses = new StringBuffer();

                for (int i = 0; i < params.length; i++) {
                    Class clazz = params[i];
                    String type = clazz.getName();
                    String paramName = toLowerFirstCase(clazz.getSimpleName());
                    paramNames.append(type + " " +  paramName);
                    paramValues.append(paramName);
                    paramClasses.append(clazz.getName() + ".class");
                    if(i > 0 && i < params.length-1){
                        paramNames.append(",");
                        paramClasses.append(",");
                        paramValues.append(",");
                    }
                }

                sb.append("@Override" + n);
                sb.append("public " + m.getReturnType().getName() + " " + m.getName() + "(" + paramNames.toString() + ") {" + n);
                sb.append("try{" + n);
                sb.append("Method m = " + interfaces[0].getName() + ".class.getMethod(\"" + m.getName() + "\",new Class[]{" + paramClasses.toString() + "});" + n);
                sb.append((hasReturnValue(m.getReturnType()) ? "return " : "") + getCaseCode("this.h.invoke(this,m,new Object[]{" + paramValues + "})",m.getReturnType()) + ";" + n);
                sb.append("}catch(Error _ex) { }");
                sb.append("catch(Throwable e){" + n);
                sb.append("throw new UndeclaredThrowableException(e);" + n);
                sb.append("}");
                sb.append(getReturnEmptyCode(m.getReturnType()));
                sb.append("}");
            }
        sb.append("}");
        return sb.toString();
    }

    private static Map<Class,Class> mappings = new HashMap<Class, Class>();
    static {
        mappings.put(int.class,Integer.class);
    }

    private static String getReturnEmptyCode(Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "return 0;";
        }else if(returnClass == void.class){
            return "";
        }else {
            return "return null;";
        }
    }

    private static String getCaseCode(String code,Class<?> returnClass){
        if(mappings.containsKey(returnClass)){
            return "((" + mappings.get(returnClass).getName() +  ")" + code + ")." + returnClass.getSimpleName() + "Value()";
        }
        return code;
    }

    private static boolean hasReturnValue(Class<?> clazz){
        return clazz != void.class;
    }

    private static String toLowerFirstCase(String src){
        char [] chars = src.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

}
