package org.example.testsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.annotation.ApiPrefix;
import org.example.testsecurity.dto.RequestLoginDTO;
import org.example.testsecurity.dto.RequestRegistrationDTO;
import org.example.testsecurity.response.ApiResponse;
import org.example.testsecurity.response.UserAuthResponse;
import org.example.testsecurity.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiPrefix
@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity<ApiResponse<UserAuthResponse>> register(
            @RequestBody @Valid RequestRegistrationDTO requestRegistrationDTO
    ) {
        var data = authService.register(requestRegistrationDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(data));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserAuthResponse>> login(
            @RequestBody @Valid RequestLoginDTO requestLoginDTO
    ) {
        var data = authService.login(requestLoginDTO);

        return ResponseEntity
                .ok(ApiResponse.success(data));
    }
}
