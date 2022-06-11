package com.service.implementations;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.ConfirmationToken;
import com.entity.User;
import com.repository.ConfirmationTokenRepository;
import com.service.interfaces.IConfirmationTokenService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationTokenService implements IConfirmationTokenService {
	@Autowired
	ConfirmationTokenRepository confirmationTokenRepository;
	
	
	
	@Override
	public ConfirmationToken add(ConfirmationToken confirmationToken) {
		return confirmationTokenRepository.save(confirmationToken);
	}

	@Override
	public int updateConfirmedAt(String token) {
		return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}

	@Override
	public Optional<ConfirmationToken> getByToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}
	

	@Override
	public void deleteToken(User user){
		ConfirmationToken ct = confirmationTokenRepository.findByUser(user);
		confirmationTokenRepository.delete(ct);
	}

	

}
