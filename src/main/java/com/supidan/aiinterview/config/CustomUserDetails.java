package com.supidan.aiinterview.config;


import com.supidan.aiinterview.domain.po.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class CustomUserDetails implements UserDetails {

    private Long    id;
    private String  username;
    private String  password;
    private Integer role;

    public CustomUserDetails(User user) {
        this.id       = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role     = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = role == 1 ? "ROLE_ADMIN" : "ROLE_USER";
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}