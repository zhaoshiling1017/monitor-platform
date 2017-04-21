package com.ducetech.app.service.impl;

import com.ducetech.app.dao.UserDAO;
import com.ducetech.app.model.Department;
import com.ducetech.app.model.Role;
import com.ducetech.app.model.User;
import com.ducetech.app.service.DepartmentService;
import com.ducetech.app.service.RoleService;
import com.ducetech.app.service.UserService;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.util.Digests;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Override
	public User getUserByLoginName(String loginName) {
		return userDAO.selectUserByLoginName(loginName);
	}

	@Override
	public List<String> getUserPermission(String userId) {
		return userDAO.selectPermissionByUserId(userId);
	}
	
	@Override
	public List<User> getUsersByDeptId(String deptId) {
		User user = new User();
		user.setDepartmentId(deptId);
		return userDAO.selectUser(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		User user = userDAO.selectUserByUserId(userId);
		List<Role> roles = roleService.getRolesByUserId(userId);
		user.setRoles(roles);
		return user;
	}

	@Override
	@Transactional
	public void addUser(User user) {
		user.setSecretKey(UUID.randomUUID().toString().replace("-", ""));
		user.setPassword(Digests.md5Hash(user.getPassword(), user.getSecretKey()));
		userDAO.insertUser(user);
		roleService.updateUserRoles(user.getUserId(), user.getRoleIds());
	}
	
	@Override
	@Transactional
	public void passwordReset(String userId, String password) {
		User user = userDAO.selectUserByUserId(userId);
		user.setPassword(password);
		userDAO.updateUser(user);
	}

	@Override
	@Transactional
	public void deleteUserById(String userId) {
		User user = userDAO.selectUserByUserId(userId);
		user.setIsDeleted("1");		// 1为删除
		userDAO.updateUser(user);
	}

	@Override
	public List<User> getAllUsers() {
		return this.getUserByPager(new BaseQuery<User>(new User())).getResults();
	}

	@Override
	public List<User> getUserByQuery(User user){
		return userDAO.selectUser(user);
	}
	
	@Override
	public PagerRS<User> getUserByPager(BaseQuery<User> query) {
		if( query != null && query.getPage() > 0 ){			//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<User> userList = userDAO.selectUser(query.getT());
		for (User user : userList) {
			List<Role> roles = roleService.getRolesByUserId(user.getUserId());
			String roleNames = "";
			for(Role role : roles){
				roleNames += role.getRoleName() + " ，";
			}
			if(roleNames.length()>0){
				roleNames = roleNames.substring(0,roleNames.length()-1);
			}
			user.setRoleNames(roleNames);
			user.setRoles(roles);
			Department dept = departmentService.getDepartmentById(user.getDepartmentId());
			if(dept!=null){
				user.setDepartment(dept);
			}
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageInfo page = new PageInfo(userList);
		PagerRS<User> pagerRS = new PagerRS<User>(userList, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDAO.updateUser(user);
		roleService.updateUserRoles(user.getUserId(), user.getRoleIds());
	}

	@Override
	public List<User> getUsersByRoleId(String roleId) {
		return userDAO.selectUsersByRoleId(roleId);
	}

	@Override
	public PagerRS<User> getUsersByRoleIdPage(BaseQuery<Role> query) {
		if( query != null && query.getPage() > 0 ){			//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<User> userList = userDAO.selectUsersByRoleId(query.getT().getRoleId());
		for (User user : userList) {
			Department dept = departmentService.getDepartmentById(user.getDepartmentId());
			if(dept!=null){
				user.setDepartment(dept);
			}
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		PageInfo page = new PageInfo(userList);
		PagerRS<User> pagerRS = new PagerRS<User>(userList, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public void updateUserPassword(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void resetPass(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void updateUserStatus(User user) {
		userDAO.updateUser(user);
	}
}
