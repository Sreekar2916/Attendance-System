package com.company.attendance.service;

import com.company.attendance.dto.request.LoginRequest;
import com.company.attendance.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}