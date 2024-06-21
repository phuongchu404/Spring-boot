package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.consts.ActiveStatus;
import com.oauth2.securityoauth.consts.ERole;
import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.dto.request.CreateUserRequest;
import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import com.oauth2.securityoauth.dto.response.JwtResponse;
import com.oauth2.securityoauth.entity.Role;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.entity.UserRole;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.repo.RoleRepository;
import com.oauth2.securityoauth.repo.UserRepository;
import com.oauth2.securityoauth.repo.UserRoleRepository;
import com.oauth2.securityoauth.security.JwtUtils;
import com.oauth2.securityoauth.security.UserDetailsImpl;
import com.oauth2.securityoauth.service.SessionService;
import com.oauth2.securityoauth.utils.MessageResponse;
import com.oauth2.securityoauth.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SessionServiceImpl implements SessionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${app.security.jwt-expiration}")
    private Long jwtExpiration;

    @Override
    public ResponseEntity<?> register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            log.error("Error: {}", Error.USERNAME_ALREADY_EXIST.getMessage());
            return ResponseEntity.status(Error.USERNAME_ALREADY_EXIST.getHttpStatus()).body(Error.USERNAME_ALREADY_EXIST.getMessage());
        }
        if(userRepository.existsByEmail(request.getEmail())){
            log.error("Error: {}", Error.EMAIL_ALREADY_EXIST.getMessage());
            return ResponseEntity.status(Error.EMAIL_ALREADY_EXIST.getHttpStatus()).body(Error.EMAIL_ALREADY_EXIST.getMessage());
        }

        User user  = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setUsername(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        Role role = roleRepository.findByName(ERole.USER.getValue()).orElseThrow(() -> new ResponseException(Error.ROLE_NOT_FOUND));
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @Override
    public ResponseEntity<?> addUser(CreateUserRequest request) {
        if(userRepository.existsByUsername(request.getUsername())){
            log.error("Error: {}", Error.USERNAME_ALREADY_EXIST.getMessage());
            return ResponseEntity.status(Error.USERNAME_ALREADY_EXIST.getHttpStatus()).body(Error.USERNAME_ALREADY_EXIST.getMessage());
        }
        if(userRepository.existsByEmail(request.getEmail())){
            log.error("Error: {}", Error.EMAIL_ALREADY_EXIST.getMessage());
            return ResponseEntity.status(Error.EMAIL_ALREADY_EXIST.getHttpStatus()).body(Error.EMAIL_ALREADY_EXIST.getMessage());
        }

        User user  = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setUsername(passwordEncoder.encode(request.getPassword()));
        user.setStatus(ActiveStatus.ACTIVE.getValue());
        userRepository.save(user);

        Set<UserRole> userRoles = new HashSet<>();
        request.getRoles().stream().forEach(item ->{
            UserRole userRole = new UserRole();
            Role role = roleRepository.findById(item).get();
            userRole.setUser(user);
            userRole.setRole(role);
            userRoles.add(userRole);
        });
        userRoleRepository.saveAll(userRoles);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        Optional<User> optional = userRepository.findByUsernameAndStatus(request.getUsername(), ActiveStatus.ACTIVE.getValue());
        if(!optional.isPresent()) {
            throw  new ResponseException(Error.INCORRECT_USERNAME);
        }
        User user = optional.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw  new ResponseException(Error.INCORRECT_PASSWORD);
        }
        String token = jwtUtils.createToken(user.getUsername());
        Set<Role> roles = userRoleRepository.findRolesByUsername(user.getUsername());
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roles(roles)
                .build();
        redisUtils.setValue(token, userDetails, jwtExpiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok(new JwtResponse(userDetails.getUsername(), roles.stream().map(Role::getName).collect(Collectors.toSet())));
    }
}
