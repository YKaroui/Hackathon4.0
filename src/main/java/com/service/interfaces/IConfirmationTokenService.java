package com.service.interfaces;

import java.util.Optional;

import com.entity.ConfirmationToken;
import com.entity.User;



public interface IConfirmationTokenService {
	ConfirmationToken add(ConfirmationToken confirmationToken);

	Optional<ConfirmationToken> getByToken(String token);

	int updateConfirmedAt(String token);

	void deleteToken(User user);

}