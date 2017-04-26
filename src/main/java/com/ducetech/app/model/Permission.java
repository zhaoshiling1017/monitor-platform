package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;


@Data
public class Permission extends BaseModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String permissionId;					//权限ID

	private String name;							//菜单、连接、按钮权限

	private String permissionStr;					//菜单权限辨识字符串

	private String parentPermissionId;				//父级菜单ID
	
	private String parentPermissionName;			//父级菜单名字

	private String comment;							//备注,说明

	private String creatorId;		                //创建人ID

	private User creator;			                //创建人

	private String createdAt;		                //创建时间

	private String isDeleted;		                //删除标记	0启用	1停用	默认0启用
}
