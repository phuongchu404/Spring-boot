package com.oauth2.securityoauth.repo;

import com.oauth2.securityoauth.entity.Role;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.entity.UserRole;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query(value = "SELECT ur.role FROM UserRole ur " +
            "inner join ur.user u where u.username = :username")
    Set<Role> findRolesByUsername(@Param("username") String username);

    @Query(value = "SELECT ur.role FROM UserRole ur " +
            "join ur.user u where u.username = :username")
    User findUserByUsername(@Param("username") String username);

    @Query(value = "SELECT ur.role FROM UserRole ur " +
            "inner join ur.user u where u.email = :email")
    Set<Role> findRolesByEmail(@Param("email") String email);
    }
