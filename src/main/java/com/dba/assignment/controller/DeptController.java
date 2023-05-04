package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.DeptDto;
import com.dba.assignment.entity.Dept;
import com.dba.assignment.entity.Location;
import com.dba.assignment.entity.LocationPK;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.DeptRepository;
import com.dba.assignment.repository.LocationRepository;
import com.fasterxml.jackson.annotation.JsonInclude;

@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class DeptController {

	@Autowired
	private DeptRepository deptRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@PostMapping(value = "/dept", produces = "application/json")
	public ResponseEntity<?> createNewDept(@RequestBody DeptDto deptDto){
		Predicate<DeptDto> deptNotNull = dpt-> dpt.getName() !=null&& !dpt.getName().isBlank();
		Predicate<DeptDto> deptLocationNotNull = dpt1->dpt1.getCountry()!=null && !dpt1.getCountry().isEmpty() &&
				                                 dpt1.getCity()!=null && !dpt1.getCity().isEmpty() && 
				                                 dpt1.getPin()!=null;
		Predicate<DeptDto> deptPredicate = deptNotNull.and(deptLocationNotNull);
		
		DeptDto deptRequestParm = Optional.ofNullable(deptDto).filter(deptPredicate).orElseThrow(()->new IllegalArgumentException("Invalid Argument"));
 
		LocationPK pk = new LocationPK();
		pk.setCountryABBR(deptRequestParm.getCountry().toUpperCase());
		pk.setPin(deptRequestParm.getPin());
		
		Optional<Location> loOptional = locationRepo.findById(pk);
		
		if(!loOptional.isPresent()) {
			return new ResponseEntity<>("Location is not available in the system", HttpStatus.BAD_REQUEST);
		}
		
		Dept newDept = new Dept();
		newDept.setName(deptRequestParm.getName().toUpperCase());
		Stream<Location> locStream = Stream.of(loOptional.get());
		newDept.setLocations(locStream.collect(Collectors.toSet()));
		
		return new ResponseEntity<>(deptRepo.save(newDept), HttpStatus.CREATED);
		
	}
	
	@GetMapping(value = "/dept/{id}", produces = "application/json")
	public Dept getDept(@PathVariable Integer id) {
		return deptRepo.findById(id).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Dept doesn;t exist")));
	}
	

	@GetMapping(value = "/dept", produces = "application/json")
	public List<Dept> getAllDept() {
		return deptRepo.findAllDept();
	}
}
