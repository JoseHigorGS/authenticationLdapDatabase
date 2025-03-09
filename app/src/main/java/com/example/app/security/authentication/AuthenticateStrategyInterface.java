package com.example.app.security.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticateStrategyInterface {
    Authentication authenticate(String username, String password);
}
