package com.ducetech.app.service.impl;

import com.ducetech.app.dao.RoleDAO;
import com.ducetech.app.dao.UserDAO;
import com.ducetech.app.model.Role;
import com.ducetech.app.model.User;
import com.ducetech.app.service.PermissionService;
import com.ducetech.app.service.RoleService;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public PagerRS<Role> getRoleByPager(BaseQuery<Role> query) {
		if(query != null && query.getPage() > 0){		//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Role> roleList = roleDAO.selectRole(query.getT());
		for(Role role : roleList){
			String names = "";
			List<User> users = userDAO.selectUsersByRoleId(role.getRoleId());
			if(users!=null && users.size()>0){
				for(User user : users){
					names += user.getName() + " ,";
				}
			}
			if(names.length()>0){
				names = names.substring(0,names.length()-1);
			}
			role.setUserNames(names);
		}
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(roleList);
		PagerRS<Role> pagerRS = new PagerRS<Role>(roleList, page.getTotal(), page.getPages());
		return pagerRS;
	}
	
	@Override
	public List<Role> getRoleByQuery(Role role) {
		return roleDAO.selectRole(role);
	}
	
	@Override
	public List<Role> getAllRoles() {
		return this.getRoleByPager(new BaseQuery<Role>(new Role())).getResults();
	}
	
	@Override
	public List<Role> getRolesByUserId(String userId) {
		return roleDAO.selectRolesByUserId(userId);
	}

	@Override
	public Role getRoleById(String id) {
		return roleDAO.selectRoleById(id);
	}

	@Override
	public void deleteRoleById(String roleId) {
		Role role = new Role();
		role.setIsDeleted("1");		//1为删除
		roleDAO.updateRole(role);
	}

	@Override
	@Transactional
	public void updateUserRoles(String userId, String roleIds) {
		//按人员ID删除拥有的所有角色
		roleDAO.deleteAllRolesByUserId(userId);
		//给人员重新存入角色
		if(StringUtils.isNotEmpty(roleIds)){
		for(String roleId : roleIds.split(",")){
			if(StringUtils.isNotEmpty(roleId)){
				roleDAO.insertRoleIdAndUserId(roleId,userId);
			}
		}
	  }
	}

	@Override
	public void updateRole(Role role) {
		roleDAO.updateRole(role);
	}

	@Override
	public void addRole(Role role) {
		roleDAO.insertRole(role);
	}

	@Override
	public List<Role> getRolesByNodeId(String nodeId) {
		return roleDAO.selectRolesByNodeId(nodeId);
	}

	@Override
	@Transactional
	public void updateRoleAndPermission(Role role) {
		roleDAO.updateRole(role);
		permissionService.setRolePermissions(role.getRoleId(), role.getPermissionIds());
	}

	@Override
	@Transactional
	public void addRoleAndPermission(Role role) {
		roleDAO.insertRole(role);
		permissionService.setRolePermissions(role.getRoleId(), role.getPermissionIds());
	}
}
