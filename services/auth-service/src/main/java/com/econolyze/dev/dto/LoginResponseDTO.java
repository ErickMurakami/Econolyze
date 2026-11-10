package com.econolyze.dev.dto;

public record LoginResponseDTO(
        String username,
        String authToken,
        String refreshToken
) {
    @Override
    public String toString() {
        return "LoginResponseDTO(tokens=[redacted])";
    }
}
