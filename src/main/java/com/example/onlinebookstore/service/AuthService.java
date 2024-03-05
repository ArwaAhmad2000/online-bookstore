package com.example.onlinebookstore.service;

import java.util.Collections;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.onlinebookstore.dto.LoginDto;
import com.example.onlinebookstore.dto.LoginResponseDto;
import com.example.onlinebookstore.dto.RegisterDto;
import com.example.onlinebookstore.entity.Role;
import com.example.onlinebookstore.entity.UserEntity;
import com.example.onlinebookstore.security.JwtGenerator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public UserEntity register(RegisterDto registerDto) {
        if (userService.existsByEmail(registerDto.getEmail())) {
            // throw exception : email exists
        }
        if (registerDto.getPassword() != registerDto.getConfirmPassword()) {
            // throw exception : passwords doesn't match
        }
        UserEntity user = new UserEntity();
        Role role = roleService.getRoleByName("USER");
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singletonList(role));
        return userService.save(user);
    }

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        ;
        String token = jwtGenerator.generateToken(authentication);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setEmail(loginDto.getEmail());
        loginResponseDto.setToken(token);
        return loginResponseDto;
    }

}
