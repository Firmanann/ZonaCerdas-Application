package com.demisnack.Eduplay.Application.user.repository;

import com.demisnack.Eduplay.Application.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmail(String email);

}
