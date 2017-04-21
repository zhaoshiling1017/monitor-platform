package com.ducetech.framework.security;

import com.ducetech.app.model.User;
import com.ducetech.app.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AuthRealm extends AuthorizingRealm {
	
	private static Logger logger = LoggerFactory.getLogger(AuthRealm.class);
	
	@Autowired
	private UserService userService;
	
	// 调用业务系统的Service，按登录名查询某个用户User
	// 授权
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// 获取当前用户
		User curUser = (User) principals.fromRealm(getName()).iterator().next();
		// 获取当前用户的权限串
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		List<String> permissions = userService.getUserPermission(curUser.getUserId());
		authorizationInfo.addStringPermissions(permissions);
		return authorizationInfo;
	}

	// 认证
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		// 拿到用户录入的用户名密码
		// 调用userService的方法，获取当前对象
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		User _user = userService.getUserByLoginName(userToken.getUsername());
		if (_user == null) {
			logger.info("User为空!");
			return null;
		} else {
			// getName,shiro可以有多个realm，告诉底层具体是哪一个realm
			AuthenticationInfo info = new SimpleAuthenticationInfo(_user,_user.getPassword(), getName());
			return info; // 登录成功
		}
	}

}