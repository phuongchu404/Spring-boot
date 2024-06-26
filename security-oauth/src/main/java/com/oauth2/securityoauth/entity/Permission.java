package com.oauth2.securityoauth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tag", nullable = false, length = 120)
    private String tag;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "method", length = 10)
    private String method;

    @Column(name = "pattern", length = 200)
    private String pattern;

    @Column(name = "is_white_list")
    private Integer isWhiteList;

    @Column(name = "create_time")
    @CreationTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateTime;

}