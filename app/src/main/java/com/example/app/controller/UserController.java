package com.example.app.controller;

import com.example.app.domain.User;
import com.example.app.domain.dtos.UserRequestDTO;
import com.example.app.security.token.TokenService;
import com.example.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {


    private UserService userService;
    private TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String authenticate(@RequestBody UserRequestDTO loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Autenticação bem-sucedida!";
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        User newUser = User.builder().username(user.getUsername()).password(encryptedPassword).userRole(user.getUserRole()).build();
        userService.save(newUser);
        return ResponseEntity.ok().build();
    }
}
