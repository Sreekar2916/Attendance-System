package com.company.attendance.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceDto {

    private Long id;
    private Long userId;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "dd-MM-yyyy hh:mm a")
    private LocalDateTime checkOut;

    private String status;
}