package com.service.interfaces;

import com.entity.User;

public interface IForgetPasswordService {
	Boolean updateResetPasswordToken(String email);

	User retrieveByResetPasswordToken(String resetPasswordToken);

	String updatePassword(User user, String newPassword);
}
