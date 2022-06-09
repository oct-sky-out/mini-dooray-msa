package com.nhnacademy.minidooraygateway.security.dto;


import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDerailsResponse implements UserDetails {
    private Long userNo;

    private String id;

    private String password;

    private String email;

    private String status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
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
        if(Objects.equals(status, "LEAVED")){
            return false;
        }
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(Objects.equals(status, "JOINED")){
            return true;
        }
        return false;
    }
}
