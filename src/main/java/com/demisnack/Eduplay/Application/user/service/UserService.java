package com.demisnack.Eduplay.Application.user.service;

import com.demisnack.Eduplay.Application.auth.dto.RegisterRequest;
import com.demisnack.Eduplay.Application.global.exception.BusinessException;
import com.demisnack.Eduplay.Application.global.exception.ErrorCode;
import com.demisnack.Eduplay.Application.roles.entity.RolesEntity;
import com.demisnack.Eduplay.Application.roles.entity.RolesStatus;
import com.demisnack.Eduplay.Application.roles.repository.RolesRepository;
import com.demisnack.Eduplay.Application.security.service.PasswordService;
import com.demisnack.Eduplay.Application.user.entity.UserEntity;
import com.demisnack.Eduplay.Application.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    //Inject Class
    private final UserRepository userRepo;
    private final PasswordService passwordService;
    private final RolesRepository roleRepo;

    //To validate existing email
    public void validateEmailAvailability(String email) {
        if (userRepo.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS);
        }
    }


}
