package com.springframework.webmvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shenyiwei on 2019/4/24.
 */
public class View {
    public final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
    private File viewFile;

    public View(File viewFile) {
        this.viewFile = viewFile;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        try {
            RandomAccessFile raf = new RandomAccessFile(this.viewFile, "r");
            String line;
            while ((line = raf.readLine()) != null) {
                line = new String(line.getBytes("ISO-8859-1"), "UTF-8");
                Pattern pattern = Pattern.compile("\\$\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String paramName = matcher.group();
                    paramName = paramName.replaceAll("\\$\\{|\\}", "");
                    Object paramValue = model.get(paramName);
                    if (paramValue == null) {
                        paramValue = "";
                    }
                    line = matcher.replaceFirst(makeStringForRegExp(paramValue.toString()));
                    matcher = pattern.matcher(line);
                }
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //处理特殊字符
    public static String makeStringForRegExp(String str) {
        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }

}
