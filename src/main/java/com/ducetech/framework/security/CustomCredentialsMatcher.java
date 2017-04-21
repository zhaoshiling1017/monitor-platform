package com.ducetech.framework.security;

import com.ducetech.app.model.User;
import com.ducetech.framework.util.Digests;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;


public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken usertoken = (UsernamePasswordToken) token; 
		//注意token.getPassword()拿到的是一个char[]，不能直接用toString()，它底层实现不是我们想的直接字符串，只能强转
		User user = (User)info.getPrincipals().getPrimaryPrincipal();
		Object tokenCredentials = Digests.md5Hash(String.valueOf(usertoken.getPassword()), user.getSecretKey());
		//Object tokenCredentials = Encrypt.md5(String.valueOf(usertoken.getPassword()),"cb7e52304f0d11e6965c00ff2c2e2b3f");  
		Object accountCredentials = getCredentials(info);  

		//将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false  
		return equals(tokenCredentials, accountCredentials);
	}
}
