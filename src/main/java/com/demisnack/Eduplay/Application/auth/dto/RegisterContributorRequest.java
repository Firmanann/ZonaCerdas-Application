package com.demisnack.Eduplay.Application.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterContributorRequest {

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    private String email;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;

    @NotBlank(message = "Link portofolio tidak boleh kosong")
    private String portofolio;

    @NotBlank(message = "Nama bank tidak boleh kosong")
    private String bankName;

    @NotBlank(message = "Nomor rekening tidak boleh kosong")
    private String bankAccount;
}