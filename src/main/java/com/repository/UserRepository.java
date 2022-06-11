package com.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User>{
	boolean existsById(Long id);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	User findByUsername(String username);

	User findByEmail(String email);

	User findByResetPasswordToken(String token);

	@Modifying
	@Query("update User u set u.firstName= :firstName, u.lastName= :lastName, u.birthdate = :birthdate where u.id= :id")
	void update(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("birthdate") Date birthdate, @Param("id") Long id);

	@Modifying
	@Query("update User u set u.email = :email where u.id= :id")
	void updateEmail(@Param("email") String email, @Param("id") Long id);

	@Modifying
	@Query("update User u set u.username = :username where u.id= :id")
	void updateUsername(@Param("username") String username, @Param("id") Long id);

	@Modifying
	@Query("update User u set u.password = :password where u.email= :email")
	void updatePassword(@Param("password") String password, @Param("email") String email);

	@Modifying
	@Query("update User u set u.enabled = TRUE where u.id= :id")
	int enableUser(@Param("id") long id);

	@Modifying
	@Query("update User u set u.locked = TRUE where u.id= :id")
	int lockUser(@Param("id") long id);

	@Modifying
	@Query("update User u set u.locked = FALSE where u.id= :id")
	int unlockUser(@Param("id") long id);

	@Query(value = "SELECT * FROM User c WHERE c.id= :id", nativeQuery = true)
	User getFavoris(@Param("id") long id);
}
