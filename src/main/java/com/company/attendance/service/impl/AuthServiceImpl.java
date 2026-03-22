package com.company.attendance.service.impl;

import com.company.attendance.dto.request.LoginRequest;
import com.company.attendance.dto.response.LoginResponse;
import com.company.attendance.entity.User;
import com.company.attendance.exception.CustomException;
import com.company.attendance.repository.UserRepository;
import com.company.attendance.security.JwtUtil;
import com.company.attendance.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new CustomException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setRole(user.getRole());
        response.setToken(token);

        return response;
    }
}