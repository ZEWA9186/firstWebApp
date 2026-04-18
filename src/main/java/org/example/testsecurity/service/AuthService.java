package org.example.testsecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.testsecurity.constant.RoleConstants;
import org.example.testsecurity.dto.RequestLoginDTO;
import org.example.testsecurity.jpa.Profile;
import org.example.testsecurity.jpa.Role;
import org.example.testsecurity.jpa.mapper.ProfileMapper;
import org.example.testsecurity.repository.ProfileRepository;
import org.example.testsecurity.repository.RoleRepository;
import org.example.testsecurity.response.errors_code.AuthError;
import org.example.testsecurity.dto.RequestRegistrationDTO;
import org.example.testsecurity.response.UserAuthResponse;
import org.example.testsecurity.exception.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileMapper profileMapper;
    private final RoleRepository roleRepository;

    public UserAuthResponse register(RequestRegistrationDTO dto) {
        if(profileRepository.existsByEmail(dto.getEmail())) {
            throw new AuthException(AuthError.REGISTRATION_FAILED);
        }

        Profile profile = profileMapper.toProfile(dto);
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role = roleRepository.findByName(RoleConstants.ROLE_USER).orElseThrow(
                () -> new IllegalStateException("Default role is not defined")
        );

        profile.setRoles(Set.of(role));

        profileRepository.save(profile);
        UserAuthResponse response = profileMapper.toUserAuthResponse(profile);
        String token = "";
        response.setToken(token);
        return response;

    }

    public UserAuthResponse login(RequestLoginDTO dto) {
        Profile profile = profileRepository.findByEmail(dto.getEmail()).orElseThrow(
                () -> new AuthException(AuthError.INVALID_CREDENTIALS)
        );
        if(!passwordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AuthException(AuthError.INVALID_CREDENTIALS);
        }

        UserAuthResponse response = profileMapper.toUserAuthResponse(profile);
        String token = "";
        response.setToken(token);

        return response;
    }

}
