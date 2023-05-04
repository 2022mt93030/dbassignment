package com.dba.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.dba.assignment.dto.UserDto;
import com.dba.assignment.entity.User;
import com.dba.assignment.service.UserService;

//@Controller
public class UserController {
	
	
	@Autowired
	UserService userService;

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		// create model object to store form data
		UserDto user = new UserDto();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register/save")
	public String registration(@Validated @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
		User existingUser = userService.findUserByEmail(userDto.getEmail());
		if (existingUser != null && existingUser.getEmailId() != null && !existingUser.getEmailId().isEmpty()) {
			result.rejectValue("email", null, "There is already an account registered with the same email");
		}
		if (result.hasErrors()) {
			model.addAttribute("user", userDto);
			return "/register";
		}
		//userService.saveUser(userDto);
		return "redirect:/register?success";
	}
}
