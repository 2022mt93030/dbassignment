package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.ProjectDto;
import com.dba.assignment.entity.Project;
import com.dba.assignment.service.ProjectService;

@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class ProjectWorkController {

	@Autowired
	private ProjectService projService;
	
	@PostMapping(value = "/project/{deptId}", produces = "application/json")
	public ResponseEntity<?> addNewProject(@PathVariable int deptId, @RequestBody ProjectDto projectDto) {
		return new ResponseEntity<>(projService.addProject(deptId, projectDto), HttpStatus.CREATED);		
	}
	
	
	@GetMapping(value = "/project/{projectId}", produces = "application/json")
	public Project getProject(@PathVariable Long projectId) {
		return projService.getProject(projectId);
	}
	
	@GetMapping(value = "/project/dept/{deptId}", produces = "application/json")
	public List<Project> getAllProject(@PathVariable Integer deptId) {
	
		return projService.getAllProjectForDept(deptId);//projService.getAllProjectForDept(deptId, paging);
	}
	
	
	
	
	
	
	
	
}
