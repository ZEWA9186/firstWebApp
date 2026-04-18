package org.example.testsecurity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.testsecurity.dto.RequestLoginDTO;
import org.example.testsecurity.dto.RequestRegistrationDTO;
import org.example.testsecurity.dto.UserAuthResponse;
import org.example.testsecurity.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class NewAuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponse> register(
            @RequestBody @Valid RequestRegistrationDTO requestRegistrationDTO
    ) {
        var data = authService.register(requestRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(
            @RequestBody @Valid RequestLoginDTO requestLoginDTO
            ){
        var data = authService.login(requestLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
