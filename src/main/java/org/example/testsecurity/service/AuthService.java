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
import org.example.testsecurity.response.errors_code.GeneralError;
import org.example.testsecurity.security.JwtService;
import org.example.testsecurity.security.ProfilePrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;

    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserAuthResponse register(RequestRegistrationDTO dto) {
        if(profileRepository.existsByEmail(dto.getEmail())) {
            throw new AuthException(AuthError.REGISTRATION_FAILED, HttpStatus.CONFLICT);
        }

        Profile profile = profileMapper.toProfile(dto);
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role role = roleRepository.findByName(RoleConstants.ROLE_USER).orElseThrow(
                () -> new IllegalStateException("Default role is not defined")
        );

        profile.setRoles(Set.of(role));
        profileRepository.save(profile);

        UserAuthResponse response = profileMapper.toUserAuthResponse(profile);
        String token = jwtService.generateJwtToken(profile);
        response.setToken(token);

        return response;
    }

    public UserAuthResponse login(RequestLoginDTO dto) {
         Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()
                )
        );

        if(authentication.getPrincipal() instanceof ProfilePrincipal profilePrincipal) {
            Profile profile = profilePrincipal.getProfile();

            UserAuthResponse response = profileMapper.toUserAuthResponse(profile);
            String token = jwtService.generateJwtToken(profile);
            response.setToken(token);

            return response;
        }

        throw new AuthException(GeneralError.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
