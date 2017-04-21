package com.ducetech.app.controller;


import com.alibaba.fastjson.JSON;
import com.ducetech.app.model.Department;
import com.ducetech.app.model.Role;
import com.ducetech.app.model.User;
import com.ducetech.app.service.DepartmentService;
import com.ducetech.app.service.RoleService;
import com.ducetech.app.service.UserService;
import com.ducetech.framework.controller.BaseController;
import com.ducetech.framework.model.BaseQuery;
import com.ducetech.framework.model.PagerRS;
import com.ducetech.framework.util.CookieUtil;
import com.ducetech.framework.util.DateUtil;
import com.ducetech.framework.util.Digests;
import com.ducetech.framework.web.view.OperationResult;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
public class UserController extends BaseController {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request) {
		if (request.getSession().getAttribute("DT_LOGIN_NAME")!=null) {
			return "redirect:/";
		}
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage() {
		SecurityUtils.getSubject().logout();
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, HttpServletResponse response, RedirectAttributesModelMap modelMap) {
		String loginName = request.getParameter("username");
		String password = request.getParameter("password");
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(loginName,password);
		if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)) {
			try{
				subject.login(token);	
				User user = (User)subject.getPrincipal();
				log.info("{}:{} login.", loginName, user.getName());
				subject.getSession().setAttribute("DT_LOGIN_USER", user);
				CookieUtil.setCookie(response, "", user.getUserId());
			}catch(Exception ex){
				//ex.printStackTrace();
				modelMap.addFlashAttribute("message", "账号或密码错误");
				return "redirect:/login";
			}
		}
		return "redirect:/";
	} 

	@RequestMapping(value = "/users/info", method = RequestMethod.GET)
	public String findUser(HttpServletRequest request, Model model) {
		User user = getLoginUser(request);
		model.addAttribute("user", user);
		return "user/user-info";
	}
	
	@RequestMapping(value = "/users/info", method = RequestMethod.POST)
	public String editUser(@ModelAttribute("form") User user, HttpServletRequest request, Model model) {
		String name = user.getName();
		
		User userInfo = getLoginUser(request);
		userInfo.setName(name);
		
//		userService.editUserInfo(userInfo);
		model.addAttribute("msg", "success");
		model.addAttribute("user", userInfo);
		return "user/user-info";
	}

	@RequestMapping(value = "/users/password", method = RequestMethod.GET)
	public String password(Model model) {
		return "/user/password";
	}

	/**
	 * @Title: person
	 * @param model
	 * @return String
	 * @Description: 跳转人员首页
	 */
	@RequestMapping(value = "/users/person", method = RequestMethod.GET)
	public String person(Model model) {
		Role role = new Role();
		role.setIsDeleted("0");
		List<Role> roles = roleService.getRoleByQuery(role);
		model.addAttribute("roles", roles);
		List<Department> depts = departmentService.getAllDepartments();
		model.addAttribute("depts", depts);
		return "/user/users";
	}

	/**
	 * @Title: personData
	 * @return void
	 * @throws Exception
	 * @Description: 人员首页数据
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseBody
	public PagerRS<User> personData(HttpServletRequest request) throws Exception {
		BaseQuery<User> query = User.getSearchCondition(User.class, request);
		query.getT().setEmployeeCode(query.getT().getEmployeeCode().trim());
		query.getT().setName(query.getT().getName().trim());
		PagerRS<User> rs = userService.getUserByPager(query);
		return rs;
	}

	/**
	 * @Title: addUser
	 * @return void
	 * @throws java.io.IOException
	 * @Description: 新增人员
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public OperationResult create(User user, HttpServletRequest request) throws IOException {
		User userInfo = getLoginUser(request);
		User us = new User();
		us.setLoginName(user.getLoginName());
		List<User> uName = userService.getUserByQuery(us);
		User ur = new User();
		ur.setEmployeeCode(user.getEmployeeCode());
		List<User> uCode = userService.getUserByQuery(ur);
		if (uName!=null && uName.size()>0) {
			return OperationResult.buildFailureResult("登录名已存在", 0);
		} else if(uCode!=null && uCode.size()>0) {
			return OperationResult.buildFailureResult("工号已存在", 0);
		} else {
			user.setCreatorId(userInfo.getUserId());
			user.setCreatedAt(DateUtil.formatDate(new Date(), DateUtil.DEFAULT_TIME_FORMAT));
			userService.addUser(user);
			return OperationResult.buildSuccessResult("成功", 1);
		}
	}

	/**
	 * @param userId
	 * @Description: 跳转人员编辑页面
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User edit(@PathVariable(value="id")String userId) throws IOException {
		User user = new User();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(userId)){
			user = userService.getUserByUserId(userId);
		}
		return user;
	}

	/**
	 * @param user
	 * @Description: 更新人员信息
	 */
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	@ResponseBody
	public OperationResult update(User user) throws IOException {
		List<User> uList = userService.getAllUsers();
		for (int i = 0; i < uList.size(); i++) {
			if(uList.get(i).getUserId().equals(user.getUserId())){
				uList.remove(i);
			}
		}
		for (User us : uList) {
			if (us.getLoginName().equals(user.getLoginName())) {
				return OperationResult.buildFailureResult("用户名已存在", 0);
			} else if (us.getEmployeeCode().equals(user.getEmployeeCode())) {
				return OperationResult.buildFailureResult("工号已存在", 0);
			}
		}
		userService.updateUser(user);
		return OperationResult.buildFailureResult("成功", 1);
	}


	/**
	 * @Description: 人员禁用启用
	 */
	@RequestMapping(value = "/users/{id}/updateStatus", method = RequestMethod.PUT)
	@ResponseBody
	public OperationResult updateStatus(@PathVariable(value="id") String userId, String isDeleted) throws IOException {
		User user = new User();
		user.setUserId(userId);
		user.setIsDeleted(isDeleted);
		userService.updateUserStatus(user);
		return OperationResult.buildFailureResult("成功", 1);
	}

	/**
	 * @Title: resetPass
	 * @Description: 重置密码为123456
	 */
	@RequestMapping(value = "/users/{id}/resetPass", method = RequestMethod.PUT)
	@ResponseBody
	public OperationResult resetPass(@PathVariable(value="id") String userId) throws IOException {
		User user = userService.getUserByUserId(userId);
		user.setPassword(Digests.md5Hash("123456", user.getSecretKey()));
		userService.resetPass(user);
		return OperationResult.buildFailureResult("重置成功，密码为：123456", 1);
	}
}
