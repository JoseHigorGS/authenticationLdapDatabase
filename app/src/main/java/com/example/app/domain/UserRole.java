package com.example.app.domain;

public enum UserRole {
    ADMIN ("ADMIN"), USER("USER");
    private String role;
    UserRole(String role) {
        this.role = role;
    }
    String getRole(){
        return this.role;
    }

}
