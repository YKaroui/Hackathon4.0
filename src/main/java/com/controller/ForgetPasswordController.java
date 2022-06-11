package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.User;
import com.service.implementations.ForgetPasswordService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/forgetPassword")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgetPasswordController {
	@Autowired
	ForgetPasswordService forgetPasswordService;

	@PostMapping("/forgot_password")
	public ResponseEntity<String> processForgotPassword(@RequestBody User user) {
		Boolean test = forgetPasswordService.updateResetPasswordToken(user.getEmail());
		if (test) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} 
	}

	@GetMapping("/reset_password")
	public ResponseEntity<String> showResetPasswordForm(@Param(value = "token") String token) {
		String msg;
		User user = forgetPasswordService.retrieveByResetPasswordToken(token);
		if (user == null){
			msg = "invalid Token";
			return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
		}
		else{
			msg = "interface change your password";
			return new ResponseEntity<>(msg, HttpStatus.OK);
		}
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<String> updatePassword(@RequestBody User user) {
		String msg = forgetPasswordService.updatePassword(user, user.getPassword());
		return new ResponseEntity<>(msg, HttpStatus.OK);

	}
}