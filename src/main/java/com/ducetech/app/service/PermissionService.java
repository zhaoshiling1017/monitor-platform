package com.ducetech.app.service;

import com.ducetech.app.model.Permission;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;

import java.util.List;

public interface PermissionService {
	
	/** 
	* @Title: getPermissionByPager  
	* @return PagerRS<Permission>
	* @Description: 条件筛选菜单权限，带分页
	*/
	PagerRS<Permission> getPermissionByPager(BaseQuery<Permission> query);
	
	/** 
	* @Title: getPermissionByQuery  
	* @param permission
	* @return List<Permission>
	* @Description: 按条件筛选菜单权限,不带分页
	*/
	List<Permission> getPermissionByQuery(Permission permission);
	
	/** 
	* @Title: getAllPermissions  
	* @return List<Permission>
	* @Description: 获取所有菜单权限
	*/
	List<Permission> getAllPermissions();
	
	/** 
	* @Title: getPermissionsByRoleId  
	* @param roleId
	* @return List<Permission>
	* @Description: 通过角色ID获取拥有的菜单权限
	*/
	List<Permission> getPermissionsByRoleId(String roleId);
	
	/** 
	* @Title: getPermissionByUserId  
	* @param userId
	* @return List<Permission>
	* @Description: 通过人员ID获取菜单权限列表
	*/
	List<Permission> getPermissionByUserId(String userId);
	
	/** 
	* @Title: addPermission  
	* @param permission
	* @return void
	* @Description: 新增菜单权限
	*/
	void addPermission(Permission permission);
	
	/** 
	* @Title: updatePermission  
	* @param permission
	* @Description: 更新菜单权限
	*/
	void updatePermission(Permission permission);
	
	/** 
	* @Title: deletePermissionById  
	* @param permissionId
	* @return void
	* @Description: 删除菜单权限
	*/
	void deletePermissionById(String permissionId);

	/** 
	* @Title: setRolePermissions  
	* @param roleId
	* @param permissionIds
	* @Description: 给角色设置权限
	*/
	void setRolePermissions(String roleId, String permissionIds);
	
	/** 
	* @Title: getPermissionById  
	* @param permissionId
	* @return Permission
	* @Description: 通过ID获取菜单权限
	*/
	Permission getPermissionById(String permissionId);

	/** 
	* @Title: getPermissionByName  
	* @param name
	* @return Permission
	* @Description: 通过名称获取菜单权限
	*/
	List<Permission> getPermissionByName(String name);
	
	/** 
	* @Title: getPermissionByPermissionStr  
	* @param permissionStr
	* @return Permission
	* @Description: 通过str获取菜单权限
	*/
	List<Permission> getPermissionByPermissionStr(String permissionStr);
	
}
