package com.ducetech.app.service;

import com.ducetech.app.model.Role;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;

import java.util.List;

/** 
* @ClassName: RoleService  
* @author gaoy
* @date 2016年8月24日 下午3:09:51 
* @Description: 角色service
*/
public interface RoleService {
	
	/** 
	* @Title: getRoleByPager  
	* @param query
	* @return PagerRS<Role>
	* @Description: 条件筛选角色，带分页
	*/
	PagerRS<Role> getRoleByPager(BaseQuery<Role> query);
	
	/** 
	* @Title: getRoleByQuery  
	* @param role
	* @return List<Role>
	* @Description: 按条件筛选角色,不带分页
	*/
	List<Role> getRoleByQuery(Role role);
	
	/** 
	* @Title: getAllRoles  
	* @return List<Role>
	* @Description: 获取所有角色
	*/
	List<Role> getAllRoles();
	
	/** 
	* @Title: getRolesByUserId  
	* @param id
	* @return List<Role>
	* @Description: 按人员ID获取拥有的角色
	*/
	List<Role> getRolesByUserId(String userId);
	
	/** 
	* @Title: getRoleById  
	* @param id
	* @return Role
	* @Description: 通过角色ID获取角色
	*/
	Role getRoleById(String id);

	/** 
	* @Title: deleteRoleById  
	* @param roleId
	* @return void
	* @Description: 通过角色ID删除角色
	*/
	void deleteRoleById(String roleId);
	
	/** 
	* @Title: updateUserRoles  
	* @param userId
	* @param roleIds
	* @Description: 设置人员的角色
	*/
	void updateUserRoles(String userId, String roleIds);

	/** 
	* @Title: updateRole  
	* @param roleId
	* @param role
	* @Description: 更新角色
	*/
	void updateRole(Role role);

	/** 
	* @Title: addRole  
	* @param role
	* @Description: 新增角色
	*/
	void addRole(Role role);

	/** 
	* @Title:    getRolesByNodeId 
	* @param  nodeId
	* @return List<Role>
	* @Description: 通过节点ID获取已拥有角色
	*/ 
	List<Role> getRolesByNodeId(String nodeId);

	/** 
	* @Title: updateRoleAndPermission  
	* @param role
	* @return void
	* @Description: 更新角色和菜单权限
	*/
	void updateRoleAndPermission(Role role);

	/** 
	* @Title: addRoleAndPermission  
	* @param role
	* @Description: 新增角色和菜单权限
	*/
	void addRoleAndPermission(Role role);
}
