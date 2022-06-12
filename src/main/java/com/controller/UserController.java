package com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.service.implementations.UserService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/retrieveAll")
	public ResponseEntity<List<User>> retrieveAll() {
		List<User> productsRetreived = userService.retrieveAll();
		if (productsRetreived.size()!=0)
			return new ResponseEntity<>(productsRetreived, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
