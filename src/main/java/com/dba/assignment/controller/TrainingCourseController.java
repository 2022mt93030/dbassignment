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
import com.dba.assignment.entity.Course;
import com.dba.assignment.entity.Skill;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.CourseRepository;
import com.dba.assignment.repository.SkillRepository;

import jakarta.validation.Valid;

@RequestMapping(APP_CONTEXT_PATH)
@RestController
public class TrainingCourseController {

	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private SkillRepository skillRepo;
	
//	@PostMapping(value = "/course", produces = "application/json")
//	public ResponseEntity<?> addNewTrainingCourse(@RequestParam Integer skillId, @RequestBody @Valid final CourseDto courseDto) {
//        
//		boolean courseExists = courseRepo.existsByTitle(courseDto.getTitle().toUpperCase());
//		System.out.println(skillId);		
//		if(courseExists) {
//			throw new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), String.format("Course %s already exist.", courseDto.getTitle())));
//		}
//		
//		Skill skillObj = Optional.ofNullable(skillId).map(id->{
//			return skillRepo.findById(id).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Skill does not exist.")));
//		}).orElse(null);
//		
//		ModelMapper modelMapper = new ModelMapper();		
//		Course course = modelMapper.map(courseDto, Course.class);
//		course.setSkill(skillObj);
//		return new ResponseEntity<>(courseRepo.save(course), HttpStatus.CREATED);
//	}
	
	@GetMapping(value = "/course", produces = "application/json")
	public List<Course> getCourses(@RequestParam Integer skillId) {
		return Optional.ofNullable(skillId).map(sid->courseRepo.findBySkill(sid)).orElse(courseRepo.findAll());
	}
	
	@GetMapping(value = "/course/{id}", produces = "application/json")
	public Course getCourse(@PathVariable Integer id) {
		return Optional.ofNullable(id).map(sid->courseRepo.findById(sid).
				orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Course does not exist.")))).
				orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Course Id is null or blank.")));
	}
	
	@PutMapping(value = "/course/{courseId}", produces = "application/json")
	public ResponseEntity<?> cancelTrainingCourse(@PathVariable final Integer courseId) {

       Course course = Optional.ofNullable(courseId).map(id->{
        	return courseRepo.findById(courseId).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Course does not exist.")));
        	
        }).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Course Id is null or blank.")));
		
       course.setIsDisabled(true);
       
       return new ResponseEntity<>(courseRepo.save(course),	HttpStatus.OK);
	}
	
	
}
