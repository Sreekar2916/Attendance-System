package com.company.attendance.dto.response;

import lombok.Data;

@Data
public class AttendanceData {

    private Long userId;
    private String checkIn;
    private String checkOut;
    private String status;
}