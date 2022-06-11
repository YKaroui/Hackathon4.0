package com.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	ADMIN, USER, ASSOCIATION;
	@Override
	public String getAuthority() {
		return "ROLE_" + name();
	}
}