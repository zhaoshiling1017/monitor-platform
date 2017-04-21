package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Role extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String roleId;				//角色ID
	
	private String roleName;			//角色名称
	
	private String comment;				//备注
	
	private String deptId;				//部门ID
	
	private String permissionIds;		//菜单权限IDS
	
	private String permissionMajorIds;	//菜单权限专业IDS
	
	private String permissionDeptIds;   //菜单权限部门IDS
	
	private String groupIds; 			//审批组IDS
	
	private List<Permission> permissions;

	private List<User> users;			//已配人员

	private String userNames;			//已配人员名字

	private String creatorId;		        //创建人ID

	private User creator;			        //创建人

	private String createdAt;		        //创建时间

	private String isDeleted;		        //删除标记	0启用	1停用	默认0启用
}
