package com.ducetech.framework.controller;

import javax.servlet.http.HttpServletRequest;

import com.ducetech.app.model.User;
import com.ducetech.app.service.UserService;
import com.ducetech.framework.util.CookieUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    UserService userService;

    public User getLoginUser(HttpServletRequest request, Model model) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(CookieUtil.USER_NAME);
        if (null == user || StringUtils.isEmpty(user.getName())) {
            String userId = CookieUtil.getLoginUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
                user = userService.getUserByUserId(userId);
            }
        }
        model.addAttribute("user", user);
        return user;
    }

    public User getLoginUser(HttpServletRequest request) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute(CookieUtil.USER_NAME);
        if (null == user || StringUtils.isEmpty(user.getName())) {
            String userId = CookieUtil.getLoginUserId(request);
            if (StringUtils.isNotEmpty(userId)) {
              user = userService.getUserByUserId(userId);
            }
        }
        return user;
    }
    
}
