package java.com;

import com.springframework.web.servlet.DispatchServlet;

import java.io.File;

/**
 * Created by shenyiwei on 2019/4/15.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(File.separator);
        System.out.println(File.separatorChar);
        System.out.println(Test.class.getClassLoader());
    }

}
