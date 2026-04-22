package org.example.testsecurity.config;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.repository.ProfileRepository;
import org.example.testsecurity.response.errors_code.AuthError;
import org.example.testsecurity.security.ProfilePrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ProfileRepository profileRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> profileRepository.findByEmail(email)
                .map(ProfilePrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(AuthError.INVALID_CREDENTIALS.name()));
    }
}
