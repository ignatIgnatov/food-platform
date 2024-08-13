package com.food.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public enum UserRole {
    ROLE_CUSTOMER,
    ROLE_RESTAURANT_OWNER,
    ROLE_ADMIN;

    public List<SimpleGrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority(this.name()));
    }
}
