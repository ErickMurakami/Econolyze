package com.econolyze.dev.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(schema = "authentication", name = "revoked_tokens")
public class RevokedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    public String jti;

    @Column(name = "expires_at", nullable = false)
    public Instant expiresAt;

    public RevokedToken(final Long id, final String jti, final Instant expiresAt) {
        this.id = id;
        this.jti = jti;
        this.expiresAt = expiresAt;
    }

    public RevokedToken() {
    }
}
