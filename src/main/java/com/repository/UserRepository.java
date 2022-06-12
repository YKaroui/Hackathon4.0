package com.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	boolean existsById(Long id);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	User findByUsername(String username);

	User findByEmail(String email);

	User findByResetPasswordToken(String token);

	@Modifying
	@Query("update User u set u.password = :password where u.email= :email")
	void updatePassword(@Param("password") String password, @Param("email") String email);

	@Modifying
	@Query("update User u set u.enabled = TRUE where u.id= :id")
	int enableUser(@Param("id") long id);
}
