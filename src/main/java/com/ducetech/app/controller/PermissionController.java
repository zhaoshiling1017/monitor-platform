package com.ducetech.app.controller;

import com.alibaba.fastjson.JSON;
import com.ducetech.app.model.Department;
import com.ducetech.app.model.Permission;
import com.ducetech.app.model.Role;
import com.ducetech.app.model.User;
import com.ducetech.app.service.DepartmentService;
import com.ducetech.app.service.PermissionService;
import com.ducetech.app.service.RoleService;
import com.ducetech.app.service.UserService;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.util.DateUtil;
import com.ducetech.framework.util.Digests;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 
* @ClassName: PermissionController 
* @Description: 人员角色管理Controller 
* @date 2016年9月19日 上午9:23:04
*
 */
@Controller
@RequestMapping("permissions")
public class PermissionController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PermissionService permissionService;


	/**
	* @Title: role
	* @param model
	* @return String
	* @Description: 角色管理首页
	*/
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String role(Model model) {
		return "/permission/role";
	}

	/**
	* @Title: roleData
	* @param request
	* @param response
	* @throws Exception
	* @Description: 角色管理数据
	*/
	@RequestMapping(value = "/roleData", method = RequestMethod.POST)
	public void roleData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseQuery<Role> query = Role.getSearchCondition(Role.class, request);
		PagerRS<Role> roles = roleService.getRoleByPager(query);
		response.getWriter().write(JSON.toJSONString(roles));
	}

	/**
	* @Title: roleStatus
	* @param roleId
	* @param isDeleted
	* @param response
	* @param request
	* @throws java.io.IOException
	* @Description: 角色禁用启用
	*/
	@RequestMapping(value = "roleStatus", method = RequestMethod.POST)
	public void roleStatus(String roleId, String isDeleted, HttpServletResponse response, HttpServletRequest request) throws IOException {
		Role role = new Role();
		role.setRoleId(roleId);
		role.setIsDeleted(isDeleted);
		roleService.updateRole(role);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}

	/**
	 *
	* @Title: addRole
	* @Description: 新增角色页面
	* @param @param model
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public String addRole(Model model) {

		return "/permission/add-role";
	}

	/**
	* @Title: saveRole
	* @param request
	* @param response
	* @throws java.io.IOException
	* @Description: 新增角色和菜单权限
	*/
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public void saveRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		User user = getLoginUser(request);
		role.setCreatorId(user.getUserId());
		role.setCreatedAt(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_TIME_FORMAT));
		roleService.addRoleAndPermission(role);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}

	/**
	 *
	* @Title: editRole
	* @Description: 编辑角色
	* @param @param model
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "/editRole/{roleId}", method = RequestMethod.GET)
	public String editRole(@PathVariable String roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "/permission/edit-role";
	}

	/**
	* @Title: updateRole
	* @param role
	* @param request
	* @param response
	* @throws java.io.IOException
	* @Description: 更新角色和菜单权限
	*/
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public void updateRole(Role role, HttpServletRequest request, HttpServletResponse response) throws IOException {
		roleService.updateRoleAndPermission(role);
		response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
	}

	/**
	 *
	* @Title: rolePerson
	* @Description: 角色拥有人员
	* @param @param model
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	@RequestMapping(value = "/rolePerson/{roleId}", method = RequestMethod.GET)
	public String rolePerson(@PathVariable String roleId, Model model) {
		Role role = roleService.getRoleById(roleId);
		model.addAttribute("role", role);
		return "/permission/role-person";
	}

	/**
	* @Title: rolePersonData
	* @param response
	 * @throws Exception
	* @Description: 角色已有人员数据
	*/
	@RequestMapping(value = "/rolePersonData", method = RequestMethod.POST)
	public void rolePersonData(HttpServletResponse response, HttpServletRequest request) throws Exception {
		BaseQuery<Role> query = Role.getSearchCondition(Role.class, request);
		PagerRS<User> pageRS = userService.getUsersByRoleIdPage(query);
		response.getWriter().write(JSON.toJSONString(pageRS));
	}

	/**
	 *
	* @Title: passwordUpdate
	* @Description: 修改用户密码
	* @param @param user
	* @param @param model
	* @param @param response
	* @param @param request
	* @param @throws IOException    设定文件
	* @return void    返回类型
	* @throws
	 */
	@RequestMapping(value = "/passwordUpdate", method = RequestMethod.POST)
	public void passwordUpdate(User user, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
		user = userService.getUserByUserId(user.getUserId());
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String comfirmPassword  = request.getParameter("comfirmPassword");
		String pw = Digests.md5Hash(password, user.getSecretKey());
		if(pw.equals(user.getPassword())){
			if(newPassword.equals(comfirmPassword)){
				user.setPassword(Digests.md5Hash(newPassword, user.getSecretKey()));
				userService.updateUserPassword(user);
				response.getWriter().write("{\"1\":\"" + "成功" + "\"}");
			}else{
				response.getWriter().write("{\"0\":\"" + "两次密码不同" + "\"}");
			}
		}else {
			response.getWriter().write("{\"0\":\"" + "旧密码错误" + "\"}");
		}
	}

	/**
	* @Title: getAllPermission
	* @param response
	* @param request
	* @throws java.io.IOException
	* @Description: 获取所有菜单权限
	*/
	@RequestMapping(value = "/getAllPermission", method = RequestMethod.GET)
	public void getAllPermission(HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<Permission> permissions = permissionService.getAllPermissions();
		response.getWriter().write(JSON.toJSONString(permissions));
	}

	/**
	* @Title: getCheckedPermission
	* @param response
	* @param request
	* @throws java.io.IOException
	* @Description: 获取角色已选的菜单权限
	*/
	@RequestMapping(value = "/getCheckedPermission", method = RequestMethod.POST)
	public void getCheckedPermission(String roleId, HttpServletResponse response, HttpServletRequest request) throws IOException {
		List<Permission> permissions = permissionService.getPermissionsByRoleId(roleId);
		response.getWriter().write(JSON.toJSONString(permissions));
	}
}
