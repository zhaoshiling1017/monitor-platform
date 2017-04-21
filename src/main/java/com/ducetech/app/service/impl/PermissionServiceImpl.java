package com.ducetech.app.service.impl;

import com.ducetech.app.dao.PermissionDAO;
import com.ducetech.app.model.Permission;
import com.ducetech.app.service.PermissionService;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	@Override
	public PagerRS<Permission> getPermissionByPager(BaseQuery<Permission> query) {
		if(query != null && query.getPage() > 0){		//如果传入offset大于0,则启用分页查询，否则不启用
			PageHelper.startPage(query.getPage(), query.getRows(), true);
		}
		List<Permission> perList = permissionDAO.selectPermission(query.getT());
		@SuppressWarnings({ "rawtypes", "unchecked" })
		PageInfo page = new PageInfo(perList);
		PagerRS<Permission> pagerRS = new PagerRS<Permission>(perList, page.getTotal(), page.getPages());
		return pagerRS;
	}

	@Override
	public List<Permission> getPermissionByQuery(Permission permission) {
		return permissionDAO.selectPermission(permission);
	}
	
	@Override
	public List<Permission> getAllPermissions() {
		return this.getPermissionByPager(new BaseQuery<Permission>(new Permission())).getResults();
	}
	
	@Override
	public List<Permission> getPermissionsByRoleId(String roleId) {
		return permissionDAO.selectPermissionsByRoleId(roleId);
	}

	@Override
	public void addPermission(Permission permission) {
		permissionDAO.insertPermission(permission);
	}

	@Override
	public void updatePermission(Permission permission) {
		permissionDAO.updatePermission(permission);
	}

	@Override
	public void deletePermissionById(String permissionId) {
		permissionDAO.deletePermissionById(permissionId);
	}

	@Override
	@Transactional
	public void setRolePermissions(String roleId, String permissionIds) {
		//按角色ID删除原有菜单权限
		permissionDAO.deleteAllPermissionsByRoleId(roleId);		
		//按角色ID重新写入菜单权限
		for (String permissionId : permissionIds.split(",")) {
			if(StringUtils.isNotEmpty(permissionId)){
				permissionDAO.insertPermissionByRoleId(roleId,permissionId);
			}
		}
	}

	@Override
	public Permission getPermissionById(String permissionId) {
		return permissionDAO.selectPermissionById(permissionId);
	}

	@Override
	public List<Permission> getPermissionByName(String name) {
		List<Permission> perList = new ArrayList<>();
		if(StringUtils.isNotEmpty(name)){
			Permission permission = new Permission();
			permission.setName(name);
			perList = permissionDAO.selectPermission(permission);
			return perList;
		}else{
			return perList;
		}
	}

	@Override
	public List<Permission> getPermissionByPermissionStr(String permissionStr) {
		List<Permission> perList = new ArrayList<>();
		if(StringUtils.isNotEmpty(permissionStr)){
			Permission permission = new Permission();
			permission.setPermissionStr(permissionStr);
			perList = permissionDAO.selectPermission(permission);
			return perList;
		}else{
			return perList;
		}
	}

	@Override
	public List<Permission> getPermissionByUserId(String userId) {
		return permissionDAO.selectPermissionByUserId(userId);
	}
}
