package com.oauth2.securityoauth.security;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private Long id;

    private String username;

    private String email;

    //private Set<Role> roles;

//    public UserDetailsImpl(Long id, String username,Set<Role> authorities) {
//        this.id = id;
//        this.username = username;
//        this.roles = authorities;
//    }

    private Set<String> roles;
    private Map<String, Object> attributes;

    public UserDetailsImpl(Long id, String email){
        this.id = id;
        this.email = email;
    }

    public UserDetailsImpl(Long id, String username,Set<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
    public UserDetailsImpl(Long id, String email,Set<String> roles,Map<String, Object> attributes) {
        this(id,email);
        this.roles = roles;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttribute(String name) {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
