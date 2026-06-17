package com.demisnack.Eduplay.Application.global.exception;

import com.demisnack.Eduplay.Application.global.response.GlobalResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // 1. Set response header ke JSON dan HTTP Status 401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 2. Gunakan format GlobalResponse standar project lu
        GlobalResponse<Void> responseBody = GlobalResponse.<Void>builder()
                .success(false)
                .error(GlobalResponse.ErrorInfo.builder()
                        .code("UNAUTHORIZED")
                        .message("Akses ditolak: Token tidak valid atau tidak ditemukan")
                        .build())
                .build();

        // 3. Konversi ke JSON String dan kirim ke client
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseBody);
    }
}