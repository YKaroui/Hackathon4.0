package com.service.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.User;
import com.repository.UserRepository;
import com.service.interfaces.IForgetPasswordService;
import com.utils.EmailService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForgetPasswordService implements IForgetPasswordService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;

	@Override
	public Boolean updateResetPasswordToken(String email) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			String token = UUID.randomUUID().toString();
			user.setResetPasswordToken(token);
			userRepository.save(user);
			log.info("password token saved.");
			String link = "http://localhost:4200/forgetPassword/reset_password?token=" + token;
			emailService.send(email, "Forget Password",
					emailService.buildEmail(email, "Click to the link to update your password", link));
			return true;
		} else {
			log.error("user not found.");
			return false;
		}
	}

	@Override
	public User retrieveByResetPasswordToken(String resetPasswordToken) {
		return userRepository.findByResetPasswordToken(resetPasswordToken);
	}

	@Override
	public String updatePassword(User user, String newPassword) {
		User UserExist = userRepository.findByEmail(user.getEmail());
		UserExist.setPassword(bCryptPasswordEncoder.encode(newPassword));
		UserExist.setResetPasswordToken(null);
		userRepository.save(UserExist);
		return "password updated";
	}
}