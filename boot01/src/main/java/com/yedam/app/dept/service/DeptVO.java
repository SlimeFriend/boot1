package com.yedam.app.dept.service;

import lombok.Data;

@Data
public class DeptVO {
	private Integer departmentId; // 부서번호, PK
	private String departmentName; // 부서명, NOT NULL
	private Integer managerId; // 부서책임자의 번호, FK
	private Integer locationId;  // 위치번호, FK

}
