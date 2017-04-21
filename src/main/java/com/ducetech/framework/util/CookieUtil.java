package com.ducetech.framework.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class CookieUtil {
	//public static final int PERSISTENTTIME = 3 * 30 * 24 * 3600;// 目前是三个月

	private static final  Logger logger = LoggerFactory.getLogger(CookieUtil.class);

	public static final String USER_NAME = "DT_LOGIN_USER";
	/**
	 * 从cookie里面获取用户登录信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getLoginUserId(final HttpServletRequest request) {
		return getCookieValue(request, USER_NAME);
	}

	public static void deleteUserFromCookie(HttpServletResponse response) {
		removeCookie(response, USER_NAME);
	}

	/**
	 * 获取Cookie的值
	 */
	public static String getCookieValue(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie c : cookies) {
				if (key.equals(c.getName())) {
					return c.getValue();
				}
			}
		}
		return null;
	}
	
	   /**
     * 生成cookie
     */
	   public static void setCookie(HttpServletResponse response, String key, String value) {
		   if (null == value) {
             value = "";
		   }
		   try {
			 value = URLEncoder.encode(value, "utf-8");
		   } catch (UnsupportedEncodingException e) {

		   }
		   if (StringUtils.isEmpty(key)) {
			   key = USER_NAME;
		   }
		   Cookie cookie = new Cookie(key, value);
		   //cookie.setDomain(null);
		   cookie.setPath("/");
		   cookie.setMaxAge(-1);// -1 means the cookie is not stored
		   cookie.setSecure(false);

       /* cookie.setSecure(secure); // 表示是否Cookie只能通过加密的连接（即SSL）发送。
		cookie.setPath(path); // 设置Cookie适用的路径
        cookie.setDomain(domain); // 设置Cookie适用的域
        cookie.setMaxAge(time); // 设置Cookie有效时间*/

        response.addCookie(cookie);
    }

	public static void removeCookie(HttpServletResponse response, String key) {
		Cookie cookie = new Cookie(key, "");
		//cookie.setDomain(null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		cookie.setSecure(false);
		response.addCookie(cookie);
	}
	
}
