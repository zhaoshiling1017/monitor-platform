/**
 * Copyright (c) 2012
 */
package com.ducetech.framework.web.filter;

import com.ducetech.framework.context.SpringContextHolder;
import com.ducetech.framework.context.WebContext;
import com.ducetech.framework.support.service.DynamicConfigService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过过滤器基于request对象初始化并缓存记录当前应用上下文路径
 */
public class WebAppContextInitFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(WebAppContextInitFilter.class);

    private static String WEB_CONTEXT_FULL_URL = null;

    private static String WEB_CONTEXT_REAL_PATH = null;

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.debug("Invoking WebAppContextInitFilter init method...");
    }

    @Override
    public void destroy() {
        logger.debug("Invoking WebAppContextInitFilter destroy method...");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {
        if (WEB_CONTEXT_FULL_URL == null) {
            HttpServletRequest request = (HttpServletRequest) req;
            DynamicConfigService dynamicConfigService = SpringContextHolder.getBean(DynamicConfigService.class);
            String contextPath = dynamicConfigService.getString("context.full.path");
            if (StringUtils.isBlank(contextPath)) {
                StringBuffer sb = new StringBuffer();
                sb.append(request.getScheme()).append("://").append(request.getServerName());
                sb.append(request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
                sb.append(request.getContextPath());
                contextPath = sb.toString();
            }
            //当前应用的完整上下文路径，一般用于邮件、短信等需要组装完整访问路径之用
            WEB_CONTEXT_FULL_URL = contextPath;
            //设置当前WEB_ROOT根目录到配置属性以便在单纯的Service运行环境取到应用根目录获取WEB-INF下相关资源
            WEB_CONTEXT_REAL_PATH = request.getServletContext().getRealPath("/");
            logger.info(" - WEB_CONTEXT_FULL_URL: {}", WEB_CONTEXT_FULL_URL);
            logger.info(" - WEB_CONTEXT_REAL_PATH: {}", WEB_CONTEXT_REAL_PATH);
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return;
        }
        WebContext.init(request, response);
        try {
            chain.doFilter(request, response);
        } finally {
            WebContext.destroy();
        }
    }

    public static String getInitedWebContextFullUrl() {
        if (WEB_CONTEXT_FULL_URL == null) {
            Assert.notNull(WEB_CONTEXT_FULL_URL, "WEB_CONTEXT_FULL_URL must NOT null");
        }
        return WEB_CONTEXT_FULL_URL;
    }

    public static String getInitedWebContextRealPath() {
        return WEB_CONTEXT_REAL_PATH;
    }

    public static void reset() {
        WEB_CONTEXT_FULL_URL = null;
        WEB_CONTEXT_REAL_PATH = null;
    }
}
