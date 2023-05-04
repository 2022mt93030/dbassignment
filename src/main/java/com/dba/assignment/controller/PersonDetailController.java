package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.CourseDto;
import com.dba.assignment.dto.EmployeeProjectDto;
import com.dba.assignment.dto.PersonDto;
import com.dba.assignment.entity.Course;
import com.dba.assignment.entity.Employee;
import com.dba.assignment.entity.EnrollAddRemoveCourse;
import com.dba.assignment.entity.EnrollAddRemoveCoursePK;
import com.dba.assignment.entity.Skill;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.CourseRepository;
import com.dba.assignment.repository.EmployeeRepository;
import com.dba.assignment.repository.EnrollAddRemoveCourseRepository;
import com.dba.assignment.repository.SkillRepository;
import com.dba.assignment.service.PersonOnboardingService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class PersonDetailController {

	@Autowired
	private PersonOnboardingService onboardingService;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private SkillRepository skillRepo;
	
	@Autowired
	private EnrollAddRemoveCourseRepository enrollAddRemoveCourseRepo;
	
	@PostMapping(value = "/employee", produces = "application/json")
	public ResponseEntity<?> onboardPerson(@RequestBody final PersonDto person) {
		return new ResponseEntity<>(this.onboardingService.onboardPerson(person),
				HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/employee/{empId}/project/{projectId}", produces = "application/json")
	public ResponseEntity<?> updateProjectForEmployee(@PathVariable long empId, @PathVariable long projectId, @RequestBody final EmployeeProjectDto empWorkDto) {
		String message = null;
		if(empWorkDto.getIsAllocation()) {
			message = this.onboardingService.allocateProject(empId, projectId, empWorkDto.getProjectJoinDate());
			return new ResponseEntity<>(message ,HttpStatus.OK);
		} else {
			message = this.onboardingService.deallocateProject(empId, projectId, empWorkDto.getComments(), empWorkDto.getGrade());
			return new ResponseEntity<>(message, HttpStatus.OK);
		}
		
	}
	
	@PutMapping(value = "/employee/{personId}/dept/{deptId}", produces = "application/json")
    public ResponseEntity<?> changeDept(@PathVariable final Long personId, @PathVariable final Integer deptId, @RequestParam(required = false) Double salaryAmt) {
		this.onboardingService.changeDept(personId, deptId, salaryAmt);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@PutMapping(value = "/employee/{personId}/role/{roleId}", produces = "application/json")
	public ResponseEntity<?> addRemoveRole(@PathVariable final Long personId, @PathVariable final Integer roleId,
			@RequestParam(required = false) boolean isRemove) {
		this.onboardingService.addRemoveRole(personId, roleId, isRemove);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping(value = "/employee/{personId}/skill/{skillId}", produces = "application/json")
	public ResponseEntity<?> addRemoveSkill(@PathVariable final Long personId, @PathVariable final Integer skillId,
			@RequestParam(required = false) boolean isRemove) {
		this.onboardingService.addRemoveSkill(personId, skillId, isRemove);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//todo test
	@PutMapping(value = "/employee/{personId}/skill/{skillId}/proficiency/{proficiency}", produces = "application/json")
	public ResponseEntity<?> updateSkillProficiency(@PathVariable final Long personId, @PathVariable final Integer skillId,
			@PathVariable final Integer proficiency) {
		this.onboardingService.updateProficiency(personId, skillId, proficiency);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping(value = "/employee/{personId}", produces = "application/json")
    public ResponseEntity<Employee> getPerson(@PathVariable final long personId) {
		return new ResponseEntity<>(this.onboardingService.getEmployee(personId), HttpStatus.OK);
	}
	
	@GetMapping(value = "/employee", produces = "application/json")
    public ResponseEntity<List<Employee>> getAllPerson(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<>(this.onboardingService.getAllEmployee(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@PostMapping(value = "/employee/{personId}/course", produces = "application/json")
	public ResponseEntity<?> addNewTrainingCourse(@PathVariable final long personId, @RequestParam Integer skillId, @RequestBody @Valid final CourseDto courseDto) {
        
		Employee emp = employeeRepo.findById(personId).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "employee does not exist")));
		boolean courseExists = courseRepo.existsByTitle(courseDto.getTitle().toUpperCase());
		System.out.println(skillId);		
		if(courseExists) {
			throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Course %s already exist.", courseDto.getTitle())));
		}
		
		Skill skillObj = Optional.ofNullable(skillId).map(id->{
			return skillRepo.findById(id).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Skill does not exist.")));
		}).orElse(null);
		
		ModelMapper modelMapper = new ModelMapper();		
		Course course = modelMapper.map(courseDto, Course.class);
		course.setSkill(skillObj);
		
		Course courseSaved = courseRepo.save(course);
		EnrollAddRemoveCoursePK pk = new EnrollAddRemoveCoursePK(personId, courseSaved.getId());
		
		EnrollAddRemoveCourse enrollAddRemove = new EnrollAddRemoveCourse();
		enrollAddRemove.setId(pk);
		enrollAddRemove.setEmp(emp);
		enrollAddRemove.setCourse(courseSaved);
		enrollAddRemove.setIs_added(true);
				
		enrollAddRemoveCourseRepo.save(enrollAddRemove);
		
		return new ResponseEntity<>("Training added", HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/employee/{personId}/course/{courseId}", produces = "application/json")
	public ResponseEntity<?> enrollTrainingCourse(@PathVariable final long personId, @PathVariable Integer courseId) {
        
		Employee emp = employeeRepo.findById(personId).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "employee does not exist")));
		Course course = courseRepo.findById(courseId).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "course does not exist")));
		
		if(course.getIsDisabled()==null || (course.getIsDisabled()!=null && !course.getIsDisabled())) {
			EnrollAddRemoveCoursePK pk = new EnrollAddRemoveCoursePK(personId, course.getId());
			
			EnrollAddRemoveCourse enrollAddRemove = new EnrollAddRemoveCourse();
			enrollAddRemove.setId(pk);
			enrollAddRemove.setEmp(emp);
			enrollAddRemove.setCourse(course);
			enrollAddRemove.setIs_enrolled(true);
					
			enrollAddRemoveCourseRepo.save(enrollAddRemove);
		} else {
			throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "course is cancelled"));
		}
		return new ResponseEntity<>("Training enrolled", HttpStatus.CREATED);
	}
	
}
