package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.CandidateVacancyDto;
import com.dba.assignment.dto.InterviewFeedbackDto;
import com.dba.assignment.entity.Candidate;
import com.dba.assignment.entity.Employee;
import com.dba.assignment.entity.InterviewOn;
import com.dba.assignment.entity.InterviewOnPK;
import com.dba.assignment.entity.Skill;
import com.dba.assignment.entity.Vacancy;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.CandidateRepository;
import com.dba.assignment.repository.EmployeeRepository;
import com.dba.assignment.repository.InterviewOnRepository;
import com.dba.assignment.repository.SkillRepository;
import com.dba.assignment.repository.VacancyRepository;

@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class CandidateController {

	@Autowired
	private VacancyRepository vacancyRepo;
	
	@Autowired
	private SkillRepository skillRepo;
	
	
	@Autowired
	private CandidateRepository candidateRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private InterviewOnRepository interviewRepo;
	
	@PostMapping(value = "/candidate/vacancy/{vacancyId}", produces = "application/json")
	public ResponseEntity<?> applyForVacancy(@RequestParam(required = false) List<Integer> skills, @RequestBody CandidateVacancyDto CandidateVacancyDto, @PathVariable Long vacancyId) {
	
		ModelMapper modelMapper = new ModelMapper();
		
		Candidate candidate = modelMapper.map(CandidateVacancyDto, Candidate.class);
		
		Vacancy vacancyAppliedFor = vacancyRepo.findById(vacancyId).orElseThrow(()->new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Vacancy does not exist.")));
		
		candidate.setVacancy(vacancyAppliedFor);
		
		
		List<Skill> skillList = Optional.ofNullable(skills).map(sList->skillRepo.findAllById(sList)).orElseThrow(()->new BadRequestException(
				new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Some or all Skills does not exist.")));
		
		candidate.getSkills().addAll(skillList);
		
		return new ResponseEntity<>(candidateRepo.save(candidate), HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/candidate/vacancy/{vacancyId}", produces = "application/json")
	public List<Candidate> getCandidateListForVacancy(@PathVariable Long vacancyId) {
	
		
		vacancyRepo.findById(vacancyId).orElseThrow(()->new BadRequestException(
						new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Vacancy does not exist.")));
		
		return candidateRepo.findByVacancy(vacancyId);		
	}
	
	@PostMapping(value = "/candidate/feedback", produces = "application/json")
	public ResponseEntity<?> addInterviewFeedback(@RequestBody InterviewFeedbackDto interviewFeedbackDto) {
		
		boolean candiateExistFlag = Optional.ofNullable(interviewFeedbackDto).map(dto->dto.getCandidateId()).map(cid->candidateRepo.existsById(cid)).orElseThrow(()->new BadRequestException(
				new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Candidate id may be null")));
		
		boolean empInterviewedExistFlag = Optional.ofNullable(interviewFeedbackDto).map(dto->dto.getEmployeeInterviewed()).map(eid->employeeRepo.existsById(eid)).orElseThrow(()->new BadRequestException(
				new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Employee id may be null.")));
		
		if(candiateExistFlag && empInterviewedExistFlag) {
			InterviewOn inteview = new InterviewOn();
			InterviewOnPK pk = new InterviewOnPK();
			pk.setCandidateId(interviewFeedbackDto.getCandidateId());
			pk.setEmpId(interviewFeedbackDto.getEmployeeInterviewed());
			inteview.setId(pk);
			inteview.setInterviewDate(new Date());
			inteview.setFeedback(interviewFeedbackDto.getFeedback());
			inteview.setStatus(interviewFeedbackDto.isStatus());
			
			interviewRepo.save(inteview);
		}
		
		return new ResponseEntity<>("Interview feedback submitted", HttpStatus.CREATED);
		
	}
	
}
