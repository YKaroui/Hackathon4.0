package com.service.implementations;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.ConfirmationToken;
import com.entity.User;
import com.entity.enums.Role;
import com.repository.UserRepository;
import com.service.interfaces.IRegistrationService;
import com.utils.EmailService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationService implements IRegistrationService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ConfirmationTokenService confirmationTokenService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;

	@Override
	public User registerUser(User user) {
		Boolean ExistsByUsername = userRepository.existsByUsername(user.getUsername());
		Boolean ExistsByEmail = userRepository.existsByEmail(user.getEmail());
		if (ExistsByUsername) {
			log.error("Username exists.");
			return null;
		} else if (ExistsByEmail) {
			log.error("Email exists.");
			return null;
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			User userSaved = userRepository.save(user);

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(5), user);
			confirmationTokenService.add(confirmationToken);

			String link = "http://localhost:8083/hackathon4.0/registration/confirm?token=" + token;
			emailService.send(user.getEmail(), "Confirm your email.", emailService.buildEmail(user.getUsername(),
					"Thank you for registering. Please click on the below link to activate your account:", link));
			return userSaved;
		}
	}

	@Override
	public User registerAdmin(User user) {
		Boolean ExistsByUsername = userRepository.existsByUsername(user.getUsername());
		Boolean ExistsByEmail = userRepository.existsByEmail(user.getEmail());
		if (ExistsByUsername) {
			log.error("Username exists.");
			return null;
		} else if (ExistsByEmail) {
			log.error("Email exists.");
			return null;
		} else {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setRole(Role.ADMIN);
			User userSaved = userRepository.save(user);

			String token = UUID.randomUUID().toString();
			ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),
					LocalDateTime.now().plusMinutes(5), user);
			confirmationTokenService.add(confirmationToken);

			String link = "http://localhost:8083/hackathon4.0/registration/confirm?token=" + token;
			emailService.send(user.getEmail(), "Confirm your email.", emailService.buildEmail(user.getUsername(),
					"Thank you for registering. Please click on the below link to activate your account:", link));
			return userSaved;
		}
	}

	@Override
	public int enableUser(long id) {
		return userRepository.enableUser(id);

	}

	@Override
	@Transactional
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenService.getByToken(token)
				.orElseThrow(() -> new IllegalStateException("token not found"));

		if (confirmationToken.getConfirmedAt() != null) {
			throw new IllegalStateException("email already confirmed");
		}

		LocalDateTime expiredAt = confirmationToken.getExpiredAt();

		if (expiredAt.isBefore(LocalDateTime.now())) {
			throw new IllegalStateException("token expired");
		}

		confirmationTokenService.updateConfirmedAt(token);
		enableUser(confirmationToken.getUser().getId());
		return "confirmed";
	}

}
