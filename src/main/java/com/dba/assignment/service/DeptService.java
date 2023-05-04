package com.dba.assignment.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dba.assignment.dto.VacancyDto;
import com.dba.assignment.entity.Vacancy;
import com.dba.assignment.repository.DeptRepository;
import com.dba.assignment.repository.VacancyRepository;

@Service
public class DeptService {

	@Autowired
	private VacancyRepository vacancyRepo;
	
	@Autowired
	private DeptRepository deptRepo;
	
	public void addDeptVacancy(final int deptId, final VacancyDto vacancyDto) {
		ModelMapper modelMapper = new ModelMapper();
		Vacancy vacancy = modelMapper.map(vacancyDto, Vacancy.class);
		deptRepo.findById(deptId).ifPresent(dept->{
			vacancy.setDept(dept);
			vacancyRepo.save(vacancy);
		});
	}
	
	public List<Vacancy> findVacancy(int deptId) {
		return vacancyRepo.findByDept(deptId);
	}
	
	public void disableVacancy(final long vacancyId) {
		
		vacancyRepo.findById(vacancyId).ifPresent(vacancy->vacancy.setStatus(false));
	}
}
