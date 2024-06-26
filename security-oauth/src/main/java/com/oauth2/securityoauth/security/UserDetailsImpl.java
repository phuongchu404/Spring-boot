package com.oauth2.securityoauth.security;

import com.oauth2.securityoauth.entity.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    //private Set<Role> roles;

//    public UserDetailsImpl(Long id, String username,Set<Role> authorities) {
//        this.id = id;
//        this.username = username;
//        this.roles = authorities;
//    }

    private Set<String> roles;

    public UserDetailsImpl(Long id, String username,Set<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
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
}
