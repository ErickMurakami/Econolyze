package com.econolyze.dev.entity;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(schema = "authentication", name = "users")
@UserDefinition
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Username
    @Column(unique = true, nullable = false)
    private String username;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Password
    @Column(nullable = false)
    private String password;
    @Roles
    @Column(nullable = false)
    private String roles;

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setRoles(final String roles) {
        this.roles = roles;
    }

    public User(final Long id, final String username, final String email, final String password, final String roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }
}