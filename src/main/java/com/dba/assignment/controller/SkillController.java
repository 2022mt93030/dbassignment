package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.entity.Skill;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.SkillRepository;

@RequestMapping(APP_CONTEXT_PATH)
@RestController
public class SkillController {

	@Autowired
	private SkillRepository skillRepo;
	
	@PostMapping(value = "/skill", produces = "application/json")
	public ResponseEntity<?> addSkill(@RequestParam final String skill) {
		Optional.ofNullable(skill).ifPresentOrElse(s->{
			boolean existFlag = skillRepo.existsByName(s.toUpperCase());
			if(existFlag) {
				throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Skill %s already exist.", s)));
			}
			
		},
		()-> new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Blank or null skill")));
		
		Skill newskill = new Skill(skill.toUpperCase());
		return new ResponseEntity<>(this.skillRepo.save(newskill),
				HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/skill", produces = "application/json")
	public List<Skill> getAllSkill() {
		List<Skill> skills = new ArrayList<>();
		this.skillRepo.findAll().forEach(action->skills.add(action));
		return skills;
	}
	
	@GetMapping(value = "/skill/{id}", produces = "application/json")
	public Skill getSkill(@PathVariable Integer id) {
			
		return Optional.ofNullable(id).map(sid->skillRepo.findById(sid).
				orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Skill does not exist.")))).
				orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Skill Id is null or blank.")));
	}
	
}
