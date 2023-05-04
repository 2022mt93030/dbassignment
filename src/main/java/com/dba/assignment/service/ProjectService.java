package com.dba.assignment.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dba.assignment.dto.ProjectDto;
import com.dba.assignment.entity.Dept;
import com.dba.assignment.entity.Project;
import com.dba.assignment.repository.DeptRepository;
import com.dba.assignment.repository.ProjectRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepo;
	
	@Autowired
	private DeptRepository deptRepo;
	
	
	public Project addProject(final int deptId, final ProjectDto projectDto) {
		ModelMapper modelMapper = new ModelMapper();
		Project p = modelMapper.map(projectDto, Project.class);
		Dept dept = deptRepo.findById(deptId).orElseThrow(()->new EntityNotFoundException("Dept of id = "+deptId+" is not found"));
		p.setDept(dept);
		return projectRepo.save(p);
	}
	
	public void editProject(final long projectId, final ProjectDto projectDto) {
		projectRepo.findById(projectId).ifPresent(prj->{
			Optional.ofNullable(projectDto).map(dto->dto.getName()).ifPresent(name->prj.setName(name));
			Optional.ofNullable(projectDto).map(dto->dto.getDescription()).ifPresent(description->prj.setDescription(description));
			Optional.ofNullable(projectDto).map(dto->dto.getClient()).ifPresent(client->prj.setClient(client));
			Optional.ofNullable(projectDto).map(dto->dto.getStartDate()).ifPresent(sDate->prj.setStartDate(sDate));
			Optional.ofNullable(projectDto).map(dto->dto.getEndDate()).ifPresent(endDate->prj.setEndDate(endDate));
			projectRepo.save(prj);
		});
	}
	
	public Project getProject(final long projectId) {
		
		return projectRepo.findById(projectId).orElse(null);
		
	}
	
	public Page<Project> getAllProjectForDept(final int deptId, final Pageable pageable) {
		
		return projectRepo.findByDeptId(deptId, pageable);
		
	}
	
	public List<Project> getAllProjectForDept(final int deptId) {
		
		return projectRepo.findWithDeptId(deptId);
		
	}
	
	
}
