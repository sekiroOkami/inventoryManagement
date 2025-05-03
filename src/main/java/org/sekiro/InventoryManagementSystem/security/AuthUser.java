package org.sekiro.InventoryManagementSystem.security;

import lombok.Builder;
import lombok.Data;
import org.sekiro.InventoryManagementSystem.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Builder
// Bridges User entity with Spring' security 's authentication mechanism
public class AuthUser implements UserDetails {

    private User user;

    public AuthUser(User user) {
        this.user = user;
    }

    // Return the user's roles/permissions
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole() != null
                ? List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                : Collections.emptyList(); // handle null roles to avoid NullPointerException
    }

    // Return the user's password
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Return the user's username
    @Override
    public String getUsername() {
        // Email is unique > find name
        return user != null ? user.getEmail() : null;
    }

    // indicates if the account is still valid
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    // indicated if the account is locked
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // indicates if the password is still valid
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // indicates if the account is active
    @Override
    public boolean isEnabled() {
        return true;
    }
}
