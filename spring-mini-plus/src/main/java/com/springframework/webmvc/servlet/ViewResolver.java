package com.springframework.webmvc.servlet;

import java.io.File;
import java.util.Locale;

/**
 * Created by shenyiwei on 2019/4/24.
 */
public class ViewResolver {
    private String DEFAULT_TEMPLATE_SUFFIX = ".html";
    private File templateRootDir;

    public ViewResolver(File templateRootDir) {
        this.templateRootDir = templateRootDir;
    }

    public View resolveViewName(String viewName, Locale locale) {
        if (viewName == null || "".equals(viewName)) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new View(templateFile);
    }

}
