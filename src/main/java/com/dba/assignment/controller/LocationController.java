package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.dto.LocationDto;
import com.dba.assignment.entity.Country;
import com.dba.assignment.entity.Location;
import com.dba.assignment.entity.LocationPK;
import com.dba.assignment.exception.BadRequestException;
import com.dba.assignment.exception.ErrorInfo;
import com.dba.assignment.repository.CountryRepository;
import com.dba.assignment.repository.LocationRepository;

@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class LocationController {

	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private CountryRepository countryRepo;
	
	@PostMapping(value = "/location", produces = "application/json")
	public ResponseEntity<?> addNewLocation(@RequestBody LocationDto location) {
		
		Predicate<LocationDto> locationAttributeNotNull = lo-> lo.getCountryAbbr()!=null && lo.getPin()!=null && lo.getCity()!=null;
		
		LocationDto loParam = Optional.ofNullable(location).filter(locationAttributeNotNull).orElseThrow(()->new IllegalArgumentException("Invalid Argument"));
		
		Optional<Country> countryOpt = countryRepo.findById(loParam.getCountryAbbr().toUpperCase());
		boolean countryExist = countryOpt.isPresent();
		
		LocationPK pk = new LocationPK();
		pk.setCountryABBR(loParam.getCountryAbbr().toUpperCase());
		pk.setPin(loParam.getPin());
		boolean isLocationExist = locationRepo.existsById(pk);
		
		if(!countryExist && isLocationExist ) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			Location newLocation = new Location();
			newLocation.setId(pk);
			newLocation.setCity(loParam.getCity().toUpperCase());
			newLocation.setCountry(countryOpt.get());
			locationRepo.save(newLocation);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/location/{country}/{pin}", produces = "application/json")
	public Location getAllLocation(@PathVariable String country, @PathVariable Integer pin) {
		return locationRepo.findById(new LocationPK(country, pin)).orElseThrow(()->new BadRequestException(new ErrorInfo(HttpStatus.BAD_REQUEST.name(), "Location doesn;t exist")));
	}
	
	@GetMapping(value = "/location/{country}", produces = "application/json")
	public List<Location> getAllLocationBy(@PathVariable String country) {
		return locationRepo.findByCountry(country);
	}
	
	
		

}
