package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.service.implementations.RegistrationService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/registration")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationController {
	@Autowired
	RegistrationService registrationService;

	@PostMapping("/user")
	public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User userSaved = registrationService.registerUser(user);
		if (userSaved != null)
			return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/admin")
	public ResponseEntity<User> addAdmin(@Valid @RequestBody User user) {
		User userSaved = registrationService.registerAdmin(user);
		if (userSaved != null)
			return new ResponseEntity<>(userSaved, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/confirm")
	public String confirm(@RequestParam("token") String token) {
		return registrationService.confirmToken(token);
	}
}
