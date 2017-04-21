package com.ducetech.app.model;

import com.ducetech.framework.model.BaseModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Department
 * @author chensf
 * @date 2015年9月25日 上午10:40:30
 * @Description: 部门
 */

@Data
public class Department extends BaseModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String departmentId; 			// 部门ID,主键,唯一标识

	private String departmentName; 			// 部门名称

	private String departmentCode; 			// 部门编号
	
	private String parentDepartmentId;		//父部门ID
	
	private String parentDepartment;		//父部门
	
	private String level;					//部门层级
	
	private String type;					//部门类别

	private String principalPersonId; 		// 负责人ID
	
	private User principalPerson; 			//负责人

	private String creatorId;		        //创建人ID

	private User creator;			        //创建人

	private String createdAt;		        //创建时间

	private String isDeleted;		        //删除标记	0启用	1停用	默认0启用
}
