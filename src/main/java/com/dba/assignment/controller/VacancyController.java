package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.VacancyDto;
import com.dba.assignment.entity.Dept;
import com.dba.assignment.entity.Vacancy;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.DeptRepository;
import com.dba.assignment.repository.VacancyRepository;

@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class VacancyController {

	@Autowired
	private VacancyRepository vacancyRepo;
	
	@Autowired
	private DeptRepository deptRepo;
	
	@PostMapping(value = "/vacancy/dept/{deptId}", produces = "application/json")
	public ResponseEntity<?> createVacancy(@PathVariable Integer deptId, @RequestBody VacancyDto vacancyDto) {
	
		ModelMapper modelMapper = new ModelMapper();
		
		Vacancy v = modelMapper.map(vacancyDto, Vacancy.class);
		
		Dept dept = deptRepo.findById(deptId).orElseThrow(()->new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept does not exist.")));
		v.setDept(dept);
		return new ResponseEntity<>(vacancyRepo.save(v), HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/vacancy/{vacancyId}", produces = "application/json")
	public ResponseEntity<?>  cancelVacancy(@PathVariable Long vacancyId) {
		Vacancy vacancy = vacancyRepo.findById(vacancyId).orElseThrow(()->new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Vacancy does not exist.")));
		vacancy.setStatus(false);
		vacancyRepo.save(vacancy);
		return new ResponseEntity<>("Vacancy cancelled or closed", HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/vacancy/{vacancyId}", produces = "application/json")
	public Vacancy showVacancy(@PathVariable Long vacancyId) {
		return vacancyRepo.findById(vacancyId).orElseThrow(()->new BadRequestException(
				new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Vacancy does not exist.")));
	}
	
	@GetMapping(value = "/vacancy/", produces = "application/json")
	public List<Vacancy> showAllVacancy() {
		return vacancyRepo.findAll();
	}
}
