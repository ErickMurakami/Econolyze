package com.econolyze.dev.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.NotBlank;

@RegisterForReflection
public record LoginRequestDTO(
        @NotBlank String username,
        @NotBlank String password
) {
    @Override
    public String toString() {
        return "LoginRequestDTO(password=[redacted])";
    }
}
