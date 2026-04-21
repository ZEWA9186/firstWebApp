package org.example.testsecurity.security;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.jpa.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@RequiredArgsConstructor
public class ProfilePrincipal implements UserDetails {

    private final Profile profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profile.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }
    //Путаница с Username и Email, указывать identityKey
    @Override
    public String getUsername() {
        return profile.getEmail();
    }



    //Методы значение которых я пойму позже))
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
