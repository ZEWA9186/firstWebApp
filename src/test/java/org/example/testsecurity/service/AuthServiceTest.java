package org.example.testsecurity.service;

import org.example.testsecurity.constant.RoleConstants;
import org.example.testsecurity.dto.RequestRegistrationDTO;
import org.example.testsecurity.exception.AuthException;
import org.example.testsecurity.jpa.Profile;
import org.example.testsecurity.jpa.Role;
import org.example.testsecurity.jpa.mapper.ProfileMapper;
import org.example.testsecurity.repository.ProfileRepository;
import org.example.testsecurity.repository.RoleRepository;
import org.example.testsecurity.response.UserAuthResponse;
import org.example.testsecurity.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ProfileMapper profileMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldThrowException_WhenUserAlreadyExists() {

        String email = "test@mail.com";
        RequestRegistrationDTO dto = new RequestRegistrationDTO();
        dto.setEmail(email);

        when(profileRepository.existsByEmail(email)).thenReturn(true);


        assertThrows(AuthException.class, () -> authService.register(dto));

        verify(profileRepository, never()).save(any());
    }

    @Test
    void register_ShouldReturnResponse_WhenCredentialsAreValid() {
        String email = "test@mail.com";
        RequestRegistrationDTO dto = new RequestRegistrationDTO();
        dto.setEmail(email);
        dto.setPassword("password");

        when(profileRepository.existsByEmail(email)).thenReturn(false);

        when(profileMapper.toProfile(dto)).thenReturn(new Profile());
        when(passwordEncoder.encode(any())).thenReturn("encodedHash");
        when(roleRepository.findByName(any())).thenReturn(Optional.of(new Role()));
        when(profileMapper.toUserAuthResponse(any())).thenReturn(new UserAuthResponse());

        UserAuthResponse response = authService.register(dto);
        assertNotNull(response);

        verify(profileRepository, times(1)).save(any());

        profileRepository.save(Profile.builder().email(email).build());

    }

    @Test
    void register_ShouldThrowException_WhenDefaultRoleNotFound() {
        RequestRegistrationDTO dto = new RequestRegistrationDTO();
        dto.setEmail("newuser@mail.com");
        dto.setPassword("password");

        when(profileRepository.existsByEmail(any())).thenReturn(false);
        when(roleRepository.findByName(RoleConstants.ROLE_USER)).thenReturn(Optional.empty());
        when(profileMapper.toProfile(dto)).thenReturn(new Profile());

        assertThrows(IllegalStateException.class, () -> authService.register(dto));
    }

}