/**
 * Copyright (c) 2012
 */
package com.ducetech.framework.web.filter;

import com.ducetech.framework.web.util.ServletUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 打印输出HTTP请求信息，一般用于开发调试
 * 生产环境把日志级别设定高于INFO即可屏蔽调试信息输出
 */
public class HttpRequestLogFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestLogFilter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logger.debug("Invoking HttpRequestLogFilter init method...");
    }

    @Override
    public void destroy() {
        logger.debug("Invoking HttpRequestLogFilter destroy method...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse reponse, FilterChain chain) throws IOException, ServletException {
        if (logger.isInfoEnabled()) {
            HttpServletRequest req = (HttpServletRequest) request;

            String uri = req.getRequestURI();
            //静态资源直接跳过
            if (uri == null || uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".gif") || uri.endsWith(".png") || uri.endsWith(".jpg")
                    || uri.endsWith(".woff") || uri.endsWith(".ico")) {
                chain.doFilter(request, reponse);
                return;
            }

            //提取verbose参数标识是否开启详细信息输出
            boolean verbose = logger.isTraceEnabled() || BooleanUtils.toBoolean(req.getParameter("verbose"));

            Map<String, String> dataMap = ServletUtil.buildRequestInfoDataMap(req, verbose);
            StringBuilder sb = new StringBuilder("HTTP Request Info:");
            for (Map.Entry<String, String> me : dataMap.entrySet()) {
                sb.append(StringUtils.rightPad("\n" + me.getKey(), 50) + " : " + me.getValue());
            }
            logger.info(sb.toString());
        }
        chain.doFilter(request, reponse);
    }
}
