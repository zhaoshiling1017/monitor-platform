package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId; 			// 用户ID,即用户的唯一标识

	private String loginName; 		// 登录名,用于用户登录,只允许有字母跟数字组成

	private String password; 		// 用户密码,由于采用shiro自带的MD5加密算法

	private String secretKey;		//秘钥,用作生成密码中的盐
	
	private String employeeCode; 	// 工号

	private String name; 			// 真实姓名

	private String email; 			// 邮件地址

	private String gender; 			// 性别

	private String phone; 			// 联系电话

	private String departmentId; 	// 部门ID

	private Department department;	//部门

	private List<Role> roles; 		// 用户拥有的角色列表
	
	private String roleIds;			//角色IDS

	private String roleNames;		//角色名字

	private String creatorId;		//创建人ID

	private User creator;			//创建人

	private String createdAt;		//创建时间

	private String isDeleted;		//删除标记	0启用	1停用	默认0启用
}
