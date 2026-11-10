package com.econolyze.dev.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationJsonTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loginRequestDeserializesExistingFieldNamesAndRetainsValidation() throws Exception {
        var request = objectMapper.readValue("{\"username\":\"user\",\"password\":\"value\"}", LoginRequestDTO.class);

        assertEquals("user", request.username());
        assertEquals("value", request.password());
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var violations = factory.getValidator().validate(new LoginRequestDTO("", ""));
            assertEquals(Set.of("username", "password"), violations.stream()
                    .map(v -> v.getPropertyPath().toString()).collect(Collectors.toSet()));
        }
        assertFalse(request.toString().contains("value"));
    }

    @Test
    void loginResponseRoundTripsWithoutAddingOrRenamingProperties() throws Exception {
        var response = new LoginResponseDTO("user", "access-value", "refresh-value");

        var json = objectMapper.valueToTree(response);

        assertEquals(3, json.size());
        assertEquals("user", json.get("username").asText());
        assertEquals("access-value", json.get("authToken").asText());
        assertEquals("refresh-value", json.get("refreshToken").asText());
        assertEquals(response, objectMapper.treeToValue(json, LoginResponseDTO.class));
    }
}
