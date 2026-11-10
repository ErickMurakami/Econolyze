package com.econolyze.dev.entity;

public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");

    private String role;

    @Override
    public String toString() {
        return role;
    }

    public static UserRole fromString(String role) {
        for (UserRole r : UserRole.values()) {
            if (r.getRole().equalsIgnoreCase(role)) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }

    private UserRole(final String role) {
        this.role = role;
    }

    private UserRole() {
    }

    public String getRole() {
        return this.role;
    }
}
