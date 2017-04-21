package com.ducetech.app.dao;

import com.ducetech.app.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/** 
* @ClassName: UserDAO  
* @author lisy
* @date 2015年11月3日 下午4:00:45 
* @Description: 用户相关方法
*/
public interface UserDAO {
	
	
	/** 
	* @Title: selectUserByLoginName  
	* @param loginName
	* @return User
	* @Description: 按登录名查询用户
	*/
	User selectUserByLoginName(@Param("loginName") String loginName);
	
	/** 
	* @Title: selectPermissionByUserId  
	* @param userId
	* @return List<String>
	* @Description: 获取某用户的全部菜单权限
	*/
	List<String> selectPermissionByUserId(@Param("userId") String userId);

	/** 
	* @Title: selectUserByUserId  
	* @return userId
	* @Description: 通过人员ID获取信息
	*/
	User selectUserByUserId(@Param("userId") String userId);

	/** 
	* @Title: selectUser  
	* @param user
	* @return List<User>
	* @Description: User通用查询
	*/
	List<User> selectUser(User user);
	
	/** 
	* @Title: addUser  
	* @param userForm
	* @Description: 保存新增的人员信息
	*/
	void insertUser(User user);
	
	/** 
	* @Title: updateUser  
	* @param userForm
	* @Description: 更新人员信息
	*/
	void updateUser(User user);

	/** 
	* @Title: deleteUserById 
	* @param userId , isDeleted
	* @Description: 启用禁用人员,默认0位启用
	*/
	void deleteUserById(@Param("userId") String userId, @Param("isDeleted") String isDeleted);

	/** 
	* @Title: selectUsersByRoleId  
	* @param roleId
	* @return List<User>
	* @Description: 通过角色ID获取人员
	*/
	List<User> selectUsersByRoleId(@Param("roleId") String roleId);
}