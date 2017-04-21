package com.ducetech.app.dao;

import com.ducetech.app.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionDAO {
	
	/** 
	* @Title: selectPermission  
	* @param permission
	* @return List<Permission>
	* @Description: 菜单权限通用查询
	*/
	List<Permission> selectPermission(Permission permission);
	
	/** 
	* @Title: selectPermissionsByRoleId  
	* @param roleId
	* @return List<Permission>
	* @Description: 通过角色ID获取拥有的菜单权限
	*/
	List<Permission> selectPermissionsByRoleId(@Param("roleId") String roleId);

	/** 
	* @Title: selectAllPermissions  
	* @return List<Permission>
	* @Description: 获取所有菜单权限
	*/
	List<Permission> selectAllPermissions();

	/** 
	* @Title: insertPermission  
	* @param permission
	* @return void
	* @Description: 新增菜单权限
	*/
	void insertPermission(Permission permission);

	/** 
	* @Title: updatePermission  
	* @param permission
	* @return void
	* @throws 
	* @Description: 更新菜单权限
	*/
	void updatePermission(Permission permission);

	/** 
	* @Title: deletePermissionById  
	* @param permissionId
	* @return void
	* @Description: 删除菜单权限
	*/
	void deletePermissionById(@Param("permissionId") String permissionId);

	/** 
	* @Title: selectPermissionById  
	* @param permissionId
	* @return Permission
	* @Description: 通过ID获取菜单权限
	*/
	Permission selectPermissionById(@Param("permissionId") String permissionId);
	
	/** 
	* @Title: selectPermissionByUserId  
	* @param userId
	* @return List<Permission>
	* @Description: 通过人员ID获取菜单权限列表
	*/
	List<Permission> selectPermissionByUserId(@Param("userId") String userId);
	
	/** 
	* @Title: deleteAllPermissionsByRoleId  
	* @param roleId
	* @Description: 按角色ID删除原有菜单权限
	*/
	void deleteAllPermissionsByRoleId(@Param("roleId") String roleId);

	/** 
	* @Title: insertPermissionByRoleId  
	* @param roleId
	* @param permissionId
	* @Description: 按角色ID重新写入菜单权限
	*/
	void insertPermissionByRoleId(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}
