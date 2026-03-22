package com.company.attendance.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String name;
    private String role;
    private String token;
}