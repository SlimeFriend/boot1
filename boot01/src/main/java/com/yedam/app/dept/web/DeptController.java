package com.yedam.app.dept.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.dept.service.DeptService;
import com.yedam.app.dept.service.DeptVO;
import com.yedam.app.emp.service.EmpVO;

@Controller
public class DeptController {
	private DeptService deptService;
	
	@Autowired
	DeptController(DeptService deptService){
		this.deptService = deptService;
	}
	
	// 전체조회
	@GetMapping("deptList")
	public String deptList(Model model) {
		List<DeptVO> list = deptService.deptList();
		System.out.println(list);
		// 페이지에 전달
		model.addAttribute("depts", list);
		return "dept/list";
		// classpath: /templates/dept/list.html
		
	}
	// 단건조회
	@GetMapping("findDept")
	public String findDept(DeptVO deptVO, Model model) {
		DeptVO dept = deptService.findDept(deptVO);
		
		model.addAttribute("depts", dept);
		return "dept/dept";
	}
	
	// 등록 - 페이지
	@GetMapping("insertDept")
	public String insertDept() {
		return "dept/insert";
	}
	// 등록 - 처리
	@PostMapping("insertDept")
	public String insertDeptProcess(DeptVO deptVO) {
		int did = deptService.deptInsert(deptVO);
		
		String url = null;
		if(did > -1) {
			// 정상적으로 등록된 경우
			url = "redirect:findDept?departmentId="+did;
			// "redirect:" 가 가능한 경우 GetMapping(redirect 도착지)
		}else {
			// 등록되지 않은 경우
			url = "redirect:deptList";
		}
		
		return url;
	}
	// 수정 - 페이지
	@GetMapping("deptUpdate")
	public String empUpdateForm(DeptVO deptVO, Model model) {
		DeptVO findVO = deptService.findDept(deptVO);
		
		// HttpServletRequest.setAttribute();  
		model.addAttribute("dept", findVO);
		
		return "dept/update";
	}
	// 수정 - 처리 : JSON
	@PostMapping("deptUpdate")
	@ResponseBody       		// AJAX
	public Map<String, Object> empUpdateAJAXJSON(@RequestBody DeptVO deptVO){
		return deptService.deptUpdate(deptVO);
	}
	// 삭제 - 처리
	@GetMapping("deptDelete")
	public String empDelete(Integer departmentId) {
		deptService.deptDelete(departmentId);
		return "redirect:deptList";
	}
}
