package com.demisnack.Eduplay.Application.user.entity;

import com.demisnack.Eduplay.Application.roles.entity.RolesEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RolesEntity role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String portofolio;

    @Column(nullable = false)
    @Builder.Default
    private Integer balance = 0;

    @Column(name = "bank_name", length = 50)
    private String bankName;

    @Column(name = "bank_account", length = 50)
    private String bankAccount;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}