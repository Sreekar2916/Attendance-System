package com.company.attendance.service;

import com.company.attendance.dto.response.AttendanceResponse;
import com.company.attendance.dto.response.DashboardResponse;
import com.company.attendance.entity.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceService {

    // 🔐 Updated with IP validation
    String checkIn(String email, String ip);

    String checkOut(String email);

    List<Attendance> getAllAttendance();

    AttendanceResponse getAttendanceByDate(LocalDate date);

    DashboardResponse getDashboardStats(LocalDate date);
}