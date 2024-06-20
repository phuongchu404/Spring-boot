package com.oauth2.securityoauth.repo;

import com.oauth2.securityoauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select (count(u) > 0) from User u where u.username = ?1 and u.email = ?2")
    boolean existsByUserNameAndEmail(String userName, String email);

    @Query("select u from User u where u.username = :userName")
    Optional<User> findByUserName(@Param("userName") String userName);


}
