package com.ducetech.framework.security;

import com.ducetech.framework.cons.GlobalConstant;
import com.ducetech.framework.context.WebContext;
import com.ducetech.framework.util.ExtStringUtil;
import com.ducetech.framework.web.annotation.IgnoreSecurity;
import org.apache.shiro.authz.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by lenzhao on 17-2-2.
 */
public class SecurityAspect {

    private static final String DEFAULT_TOKEN_NAME = "X-Token";

    private TokenManager tokenManager;
    private String tokenName;

    public void setTokenManager(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void setTokenName(String tokenName) {
        if (ExtStringUtil.isBlank(tokenName)) {
            tokenName = DEFAULT_TOKEN_NAME;
        }
        this.tokenName = tokenName;
    }

    public Object execute(ProceedingJoinPoint pjp) throws Throwable {
        String path = "";
        if (null != WebContext.getRequest()) {
            path = WebContext.getRequest().getServletPath();
        }
        if (!ExtStringUtil.isBlank(path) && path.startsWith(GlobalConstant.API_PATH_PREFIX)) {
            // 从切点上获取目标方法
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            // 若目标方法忽略了安全性检查，则直接调用目标方法
            if (method.isAnnotationPresent(IgnoreSecurity.class)) {
                return pjp.proceed();
            }
            // 从 request header 中获取当前 token
            String token = WebContext.getRequest().getHeader(tokenName);
            // 检查 token 有效性
            if (ExtStringUtil.isBlank(token) || !tokenManager.checkToken(token)) {
                String message = String.format("token [%s] is invalid", token);
                throw new UnauthorizedException(message);
            }
        }
        // 调用目标方法
        return pjp.proceed();
    }
}
