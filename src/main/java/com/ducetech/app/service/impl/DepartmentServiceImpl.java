package com.ducetech.app.service.impl;

import com.ducetech.app.dao.DepartmentDAO;
import com.ducetech.app.model.Department;
import com.ducetech.app.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentDAO departmentDAO;

	@Override
	public List<Department> getAllDepartments() {
		return departmentDAO.selectAllDepartments();
	}

	@Override
	public Department getDepartmentById(String id) {
		return departmentDAO.selectDepartmentById(id);
	}

	@Override
	public void addDepartment(Department department) {
		departmentDAO.insertDepartment(department);
	}

	@Override
	public void updateDepartment(Department department) {
		departmentDAO.updateDepartment(department);
	}

	@Override
	public void deleteDepartmentById(String id) {
		departmentDAO.deleteDepartmentById(id);
	}

	@Override
	public List<Department> findDepartmentByCond(Department department) {
		return departmentDAO.selectDepartmentByCond(department);
	}

	@Override
	public String getMaxDepartmentCode() {
		return departmentDAO.selectMaxDepartmentCode();
	}

	@Override
	public List<Department> getDepartmentByType(String type) {
		return departmentDAO.selectDepartmentByType(type);
	}

	@Override
	public List<Department> getChildrenDepartments(String departmentId) {
		return departmentDAO.selectChildrenDepartments(departmentId);
	}

	@Override
	public Department findDepartmentCodeById(String id) {
		return departmentDAO.selectDepartmentCodeById(id);
	}
}
