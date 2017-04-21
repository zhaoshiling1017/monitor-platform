package com.ducetech.app.dao;


import com.ducetech.app.model.Department;

import java.util.List;


/**
 * @author Roger
 *
 */
public interface DepartmentDAO {
	
	List<Department> selectAllDepartments();
	
	Department selectDepartmentById(String departmentId);
	
	void insertDepartment(Department department);
	
	void updateDepartment(Department department);
	
	void deleteDepartmentById(String departmentId);
	
	List<Department> selectDepartmentByCond(Department department);
	
	Department selectDepartmentCodeById(String id);
	/** 
	* @Title: selectDepartmentByType  
	* @param type
	* @return List<Department>
	* @Description: 通过类型拿到相应的部门列表,类型有:部室,项目部,维修部等
	*/
	List<Department> selectDepartmentByType(String type);
	
	String selectMaxDepartmentCode();

	/** 
	* @Title: selectChildrenDepartments  
	* @param departmentId
	* @return List<Department>
	* @Description: 通过ID得到一个部门的子部门
	*/
	List<Department> selectChildrenDepartments(String departmentId);
	
}
