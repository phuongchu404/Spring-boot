package com.oauth2.securityoauth.repo;

import com.oauth2.securityoauth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}