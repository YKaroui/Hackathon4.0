package com.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.entity.enums.Role;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Table(name = "USER", uniqueConstraints = { @UniqueConstraint(name = "User_email_unique", columnNames = "email"),
		@UniqueConstraint(name = "User_username_unique", columnNames = "username") })
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable, UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name = "FIRST_NAME", nullable = false)
	@NotBlank(message = "Firstname required")
	String firstName;
	@Column(name = "LAST_NAME", nullable = false)
	@NotBlank(message = "Lastname required")
	String lastName;
	@Column(name = "BIRTHDATE", nullable = false)
	@Temporal(TemporalType.DATE)
	@Past(message = "birthdate not valid")
	Date birthdate;
	@Column(name = "USERNAME", nullable = false)
	@NotBlank(message = "Username required")
	String username;
	@Column(name = "EMAIL", nullable = false)
	@NotBlank(message = "Email required")
	@Email(message = "email not valid")
	String email;
	@Column(name = "PASSWORD", length = 100, nullable = false)
	@NotBlank(message = "Password required")
	@Size(min = 8, max = 60, message = "password must have 8 to 20 caracters.")
	String password;
	@Column(name = "ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	Role role;
	@Column(name = "LOCKED", nullable = false)
	Boolean locked = false;
	@Column(name = "ENABLED", nullable = false)
	Boolean enabled = false;
	@Column(name = "RESET_PASSWORD_TOKEN", nullable = true)
	String resetPasswordToken;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "user")
	Set<Product> products;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "user")
	Set<Event> events;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getAuthority());
		return Collections.singletonList(authority);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
