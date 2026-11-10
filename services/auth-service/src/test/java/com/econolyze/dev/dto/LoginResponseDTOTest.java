package com.econolyze.dev.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginResponseDTOTest {
    @Test
    void canonicalConstructorPreservesResponseValues() {
        var response = new LoginResponseDTO("user", "access-value", "refresh-value");

        assertEquals("user", response.username());
        assertEquals("access-value", response.authToken());
        assertEquals("refresh-value", response.refreshToken());
    }

    @Test
    void diagnosticStringDoesNotExposeTokens() {
        var response = new LoginResponseDTO("user", "access-value", "refresh-value");

        assertFalse(response.toString().contains("access-value"));
        assertFalse(response.toString().contains("refresh-value"));
    }
}
