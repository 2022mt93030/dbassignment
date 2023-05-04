package com.dba.assignment.controller;

import static com.dba.assignment.service.Constant.APP_CONTEXT_PATH;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dba.assignment.entity.UserCredentials;
import com.dba.assignment.service.UserService;

//@Controller
@RestController
@RequestMapping(APP_CONTEXT_PATH)
public class LoginController {

	@Autowired
	private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestParam("username") String username, @RequestParam("password") String password)  {
	
		UserCredentials userDetails = userService.findUserByUserName(username);
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("INVALID_USERNAME PASSWORD_CREDENTIALS");
		}
		
        return new ResponseEntity<>("login successful", HttpStatus.OK);
    }
	
	public void disableLogin() {
		
	}
}
