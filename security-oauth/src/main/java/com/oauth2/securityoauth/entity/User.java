package com.oauth2.securityoauth.entity;

import com.oauth2.securityoauth.consts.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "username", nullable = false, length = 100)
//    @Size(min = 1, max = 100)
//    private String username;
//
//    @Column(name = "password", nullable = false, length = 100)
//    private String password;

    @Column(name = "username",length = 100)
    @Size(min = 1, max = 100)
    private String username;

    @Column(name = "password",length = 100)
    private String password;

    @ColumnDefault("b'1'")
    @Column(name = "status")
    private Boolean status;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "phone_number", length = 10)
    private String phoneNumber;

    @Column(name = "create_time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    @Column(name = "reset_password_token", length= 30)
    private String resetPasswordToken;

    @Size(max = 64)
    @Column(name = "otp", length = 64)
    private String otp;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;

    @Column(name = "provider", length = 50)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    // add columns image_url, email_verified, provider in OAuth2
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "email_verified")
    private boolean emailVerified = false;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "name")
    private String name;

}