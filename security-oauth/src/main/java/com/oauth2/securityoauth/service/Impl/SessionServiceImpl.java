package com.oauth2.securityoauth.service.Impl;

import com.oauth2.securityoauth.consts.ActiveStatus;
import com.oauth2.securityoauth.consts.ERole;
import com.oauth2.securityoauth.consts.Error;
import com.oauth2.securityoauth.dto.request.CreateUserRequest;
import com.oauth2.securityoauth.dto.request.LoginRequest;
import com.oauth2.securityoauth.dto.request.RegisterRequest;
import com.oauth2.securityoauth.dto.response.JwtResponse;
import com.oauth2.securityoauth.dto.response.MfaTokenData;
import com.oauth2.securityoauth.dto.response.MfaVerificationResponse;
import com.oauth2.securityoauth.entity.ConfirmationToken;
import com.oauth2.securityoauth.entity.Role;
import com.oauth2.securityoauth.entity.User;
import com.oauth2.securityoauth.entity.UserRole;
import com.oauth2.securityoauth.exception.ResponseException;
import com.oauth2.securityoauth.repo.ConfirmationTokenRepository;
import com.oauth2.securityoauth.repo.RoleRepository;
import com.oauth2.securityoauth.repo.UserRepository;
import com.oauth2.securityoauth.repo.UserRoleRepository;
import com.oauth2.securityoauth.security.JwtUtils;
import com.oauth2.securityoauth.security.UserDetailsImpl;
import com.oauth2.securityoauth.service.EmailService;
import com.oauth2.securityoauth.service.SessionService;
import com.oauth2.securityoauth.service.TotpManager;
import com.oauth2.securityoauth.utils.JsonConvert;
import com.oauth2.securityoauth.utils.MessageResponse;
import com.oauth2.securityoauth.utils.RedisUtils;
import dev.samstevens.totp.exceptions.QrGenerationException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
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

    @Autowired
    private EmailService emailService;

    @Value("${app.security.jwt-expiration}")
    private Long jwtExpiration;

    @Value("${app.security.token-prefix}")
    private String TOKEN_PREFIX;

    private static final BytesKeyGenerator DEFAULT_TOKEN_GENERATOR = KeyGenerators.secureRandom(15);

    private static final Charset US_ASCII = Charset.forName("US-ASCII");

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

//    @Autowired
//    private TotpManager totpManager;



    @Override
    public ResponseEntity<?> register(RegisterRequest request) throws MessagingException {
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

        this.sendRegistrationConfirmationEmail(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

//    @Override
//    public ResponseEntity<?> register(RegisterRequest request) throws MessagingException, QrGenerationException {
//        if(userRepository.existsByUsername(request.getUsername())){
//            log.error("Error: {}", Error.USERNAME_ALREADY_EXIST.getMessage());
//            return ResponseEntity.status(Error.USERNAME_ALREADY_EXIST.getHttpStatus()).body(Error.USERNAME_ALREADY_EXIST.getMessage());
//        }
//        if(userRepository.existsByEmail(request.getEmail())){
//            log.error("Error: {}", Error.EMAIL_ALREADY_EXIST.getMessage());
//            return ResponseEntity.status(Error.EMAIL_ALREADY_EXIST.getHttpStatus()).body(Error.EMAIL_ALREADY_EXIST.getMessage());
//        }
//
//        User user  = new User();
//        user.setUsername(request.getUsername());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        userRepository.save(user);
//
//        Role role = roleRepository.findByName(ERole.USER.getValue()).orElseThrow(() -> new ResponseException(Error.ROLE_NOT_FOUND));
//        UserRole userRole = new UserRole();
//        userRole.setUser(user);
//        userRole.setRole(role);
//        userRoleRepository.save(userRole);
//
//        this.sendRegistrationConfirmationEmail(user);
//
//        String qrCode = totpManager.getQRCode(user.getSecretKey());
//
//        MfaTokenData tokenData = MfaTokenData.builder()
//                .mfaCode(user.getSecretKey())
//                .qrCode(qrCode).build();
//
////        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
//        return ResponseEntity.ok(tokenData);
//    }

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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
            throw new ResponseException(Error.INCORRECT_USERNAME);
        }
        User user = optional.get();
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw  new ResponseException(Error.INCORRECT_PASSWORD);
        }
        String token = jwtUtils.createToken(user.getUsername());
        Set<Role> roles = userRoleRepository.findRolesByUsername(user.getUsername());
        Set<String> stringRoles = roles.stream().map(Role::getName).collect(Collectors.toSet());
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUsername(), stringRoles);
        redisUtils.setValue(token, JsonConvert.convertObjectToJson(userDetails), jwtExpiration, TimeUnit.SECONDS);
        return ResponseEntity.ok(new JwtResponse(token,userDetails.getUsername(), roles.stream().map(Role::getName).collect(Collectors.toSet())));
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = parseJwt(request);
        if(token == null) {
            return ResponseEntity.ok("logout success");
        }
        boolean validateToken = redisUtils.deleteKey(token);

        return validateToken ? ResponseEntity.ok("logout successfully") : ResponseEntity.badRequest().body("Login failed");
    }

//    @Override
//    public ResponseEntity<?> verifyTotp(String code, String username) {
//        User user = userRepository.findByUserName(username).orElseThrow(() -> new ResponseException(Error.USER_NOT_FOUND));
//        MfaVerificationResponse mfaVerificationResponse = MfaVerificationResponse.builder()
//                .username(username)
//                .tokenValid(Boolean.FALSE)
//                .message("Token is not valid. Please try again.")
//                .build();
//        if(totpManager.verifyTotp(code, user.getSecretKey())){
//            mfaVerificationResponse = MfaVerificationResponse.builder()
//                    .username(username)
//                    .tokenValid(Boolean.TRUE)
//                    .message("Token is valid")
//                    .build();
//        }
//        return ResponseEntity.ok(mfaVerificationResponse);
//    }

    private String parseJwt(HttpServletRequest request){
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(header)&& header.startsWith(TOKEN_PREFIX)){
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    private void sendRegistrationConfirmationEmail(User user) throws MessagingException {
        String tokenValue = new String(Base64.encodeBase64URLSafe(DEFAULT_TOKEN_GENERATOR.generateKey()), US_ASCII);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
//        confirmationToken.setConfirmationToken(tokenValue);
//        confirmationToken.setUser(user);
        confirmationTokenRepository.save(confirmationToken);
        emailService.sendConfirmationEmail(confirmationToken);

    }
}
