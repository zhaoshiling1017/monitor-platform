package com.ducetech.app.dao;

import com.ducetech.app.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
* @ClassName: RoleDAO 
* @Description: 角色DAO接口
* @author gaoy
* @date 2016年9月9日 下午4:58:21 
*/ 
public interface RoleDAO {
	
	/** 
	* @Title: selectRole  
	* @param t
	* @return List<Role>
	* @Description: 角色通用查询
	*/
	List<Role> selectRole(Role role);
	
	/** 
	* @Title: selectRoleById  
	* @param id
	* @return Role
	* @Description: 通过角色ID获取角色
	*/
	Role selectRoleById(@Param("roleId") String roleId);

	/** 
	* @Title: insertRole  
	* @Description: 新增角色
	*/
	void insertRole(Role role);

	/** 
	* @Title: updateRole 
	* @param fole
	* @return void
	* @Description: 更新角色
	*/
	void updateRole(Role role);

	/** 
	* @Title: selectRolesByUserId  
	* @param UserId
	* @return List<Role>
	* @Description: 按人员ID获取拥有的角色
	*/
	List<Role> selectRolesByUserId(@Param("userId") String userId);
	
	/** 
	* @Title: insertUserManagerRole  
	* @param role
	* @Description: 新增创建角色人ID和角色ID
	*/
	void insertUserManagerRole(Role role);

	/** 
	* @Title: deleteUserManagerRoleByUserId  
	* @param createById
	* @Description: 根据人员ID删除创建角色人ID和角色ID 
	*/ 
	void deleteUserManagerRoleByUserId(@Param("creatorId") String creatorId);

	/** 
	* @Title: selectAllRolesByUserId  
	* @param userId
	* @return List<Role>
	* @Description: 根据人员ID获取可以管理的角色
	*/
	List<Role> selectAllRolesByUserId(@Param("userId") String userId);

	/** 
	* @Title: deleteAllRolesByUserId  
	* @param userId
	* @return void
	* @Description: 按人员ID删除拥有的所有角色
	*/
	void deleteAllRolesByUserId(@Param("userId") String userId);

	/** 
	* @Title: insertRoleIdAndUserId  
	* @param roleId
	* @param userId
	* @return void
	* @Description: 给人员重新存入角色
	*/
	void insertRoleIdAndUserId(@Param("roleId") String roleId, @Param("userId") String userId);

	/** 
	* @Title:    selectRolesByNodeId 
	* @param  nodeId
	* @return List<Role>
	* @Description: 通过节点ID获取已拥有角色
	*/  
	List<Role> selectRolesByNodeId(@Param("nodeId") String nodeId);
}
