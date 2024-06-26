package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.entity.Role;
import com.oauth2.securityoauth.repo.RoleRepository;
import com.oauth2.securityoauth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public ResponseEntity<?> findAll() {
        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }
}
