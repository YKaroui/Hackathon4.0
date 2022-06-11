package com.service.interfaces;

import com.entity.User;

public interface IRegistrationService {
	User registerUser(User user);
	User registerAdmin(User user);


	int enableUser(long id);
	
	String confirmToken(String token);

}
