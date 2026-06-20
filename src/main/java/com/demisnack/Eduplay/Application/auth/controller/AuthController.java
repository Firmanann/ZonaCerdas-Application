package com.demisnack.Eduplay.Application.auth.controller;

import com.demisnack.Eduplay.Application.auth.dto.*;
import com.demisnack.Eduplay.Application.auth.service.AuthService;
import com.demisnack.Eduplay.Application.global.response.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController (AuthService authService){
        this.authService = authService;
    }

    //Endpoint Register User Biasa
    @PostMapping("/register/user")
    public ResponseEntity<GlobalResponse<Void>> register (@Valid @RequestBody RegisterUserRequest request) {

        // Eksekusi logic register di service
        authService.register(request);

        // Desain format GlobalResponse standar dengan data null
        GlobalResponse<Void> response = GlobalResponse.<Void>builder()
                .success(true)
                .data(null)
                .build();

        // Return status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Endpoint Register Contributor
    @PostMapping("/register/contributor")
    public ResponseEntity<GlobalResponse<Void>> registerContributor (@Valid @RequestBody RegisterContributorRequest request) {

        // Eksekusi logic register khusus kreator
        authService.registerContributor(request);

        // Desain format GlobalResponse standar dengan data null
        GlobalResponse<Void> response = GlobalResponse.<Void>builder()
                .success(true)
                .data(null)
                .build();

        // Return status 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Endpoint Login
    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        // Eksekusi logic login di service
        LoginResponse responseData = authService.login(request);

        // Desain GlobalResponse standar
        GlobalResponse<LoginResponse> response = GlobalResponse.<LoginResponse>builder()
                .success(true)
                .data(responseData)
                .build();

        // Return 200 OK
        return ResponseEntity.ok(response);
    }

    //Endpoint Logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    //Endpoint Get Me (Profil)
    @GetMapping("/me")
    public ResponseEntity<GlobalResponse<UserProfileResponse>> getMe(Principal principal) {

        // Principal = email user
        UserProfileResponse responseData = authService.getMe(principal.getName());

        GlobalResponse<UserProfileResponse> response = GlobalResponse.<UserProfileResponse>builder()
                .success(true)
                .data(responseData)
                .build();

        return ResponseEntity.ok(response);
    }
}