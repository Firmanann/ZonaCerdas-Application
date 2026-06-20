package com.demisnack.Eduplay.Application.auth.service;

import com.demisnack.Eduplay.Application.auth.dto.*;
import com.demisnack.Eduplay.Application.global.exception.BusinessException;
import com.demisnack.Eduplay.Application.global.exception.ErrorCode;
import com.demisnack.Eduplay.Application.global.jwt.customuserdetail.CustomUserDetails;
import com.demisnack.Eduplay.Application.global.jwt.service.JwtService;
import com.demisnack.Eduplay.Application.user.entity.UserEntity;
import com.demisnack.Eduplay.Application.user.repository.UserRepository;
import com.demisnack.Eduplay.Application.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;


    //1. Register user
    public void register(RegisterUserRequest request){
        userService.createUser(request);
    }

    // 2. Register Contributor
    public void registerContributor(RegisterContributorRequest request){
        userService.createContributor(request);
    }

    // 3. Login
    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.EMAIL_PASSWORD_INVALID);
        }

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        CustomUserDetails customUser = new CustomUserDetails(user);
        String accessToken = jwtService.generateToken(customUser);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getStatus())
                .build();
    }

    // 4. Get Me (Profil)
    public UserProfileResponse getMe(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.EMAIL_NOT_FOUND));

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getStatus())
                .balance(user.getBalance())
                .bankName(user.getBankName())
                .bankAccount(user.getBankAccount())
                .createdAt(user.getCreatedAt())
                .build();
    }
}