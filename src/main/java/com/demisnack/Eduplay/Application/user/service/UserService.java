package com.demisnack.Eduplay.Application.user.service;

import com.demisnack.Eduplay.Application.auth.dto.RegisterUserRequest;
import com.demisnack.Eduplay.Application.auth.dto.RegisterContributorRequest;
import com.demisnack.Eduplay.Application.global.exception.BusinessException;
import com.demisnack.Eduplay.Application.global.exception.ErrorCode;
import com.demisnack.Eduplay.Application.roles.entity.RolesEntity;
import com.demisnack.Eduplay.Application.roles.repository.RolesRepository;
import com.demisnack.Eduplay.Application.security.service.PasswordService;
import com.demisnack.Eduplay.Application.user.entity.UserEntity;
import com.demisnack.Eduplay.Application.user.repository.UserRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public UserEntity createUser(RegisterUserRequest request) {

        //Validasi ketersediaan email
        validateEmailAvailability(request.getEmail());

        //Cari role USER
        RolesEntity userRole = roleRepo.findByStatus("USER")
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

        //Hash Password
        String hashedPassword = passwordService.hashPassword(request.getPassword());

        //Desain objek
        UserEntity newUser = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(userRole)

                .balance(100000)
                .build();

        return userRepo.save(newUser);
    }

    //2.Method untuk Register Contributor
    @Transactional
    public UserEntity createContributor(RegisterContributorRequest request) {

        //Validasi ketersediaan email
        validateEmailAvailability(request.getEmail());

        //Cari role CONTRIBUTOR
        RolesEntity contributorRole = roleRepo.findByStatus("CONTRIBUTOR")
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

        //Hash Password
        String hashedPassword = passwordService.hashPassword(request.getPassword());

        //Membuat objek baru, plus masukin data bank
        UserEntity newUser = UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(contributorRole)
                .portofolio(request.getPortofolio())
                .bankName(request.getBankName())
                .bankAccount(request.getBankAccount())
                .build();

        return userRepo.save(newUser);
    }
}