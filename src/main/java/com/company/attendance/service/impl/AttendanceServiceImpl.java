package com.company.attendance.service.impl;

import com.company.attendance.dto.response.AttendanceData;
import com.company.attendance.dto.response.AttendanceResponse;
import com.company.attendance.dto.response.DashboardResponse;
import com.company.attendance.entity.Attendance;
import com.company.attendance.entity.User;
import com.company.attendance.exception.CustomException;
import com.company.attendance.repository.AttendanceRepository;
import com.company.attendance.repository.UserRepository;
import com.company.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    // 🔐 WIFI VALIDATION
    private boolean isValidOfficeIp(String ip) {
        return ip.startsWith("192.168.")
                || ip.startsWith("10.")
                || ip.startsWith("172.")
                || ip.equals("127.0.0.1");
    }

    @Override
    public String checkIn(String email, String ip) {

        if (!isValidOfficeIp(ip)) {
            throw new CustomException("Access Denied: Not in office network");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        Optional<Attendance> existing = attendanceRepository
                .findByUserIdAndCheckOutIsNull(user.getId());

        if (existing.isPresent()) {
            throw new CustomException("Already checked in");
        }

        LocalDateTime now = LocalDateTime.now();

        // ⏱ Late detection
        LocalTime officeTime = LocalTime.of(9, 30);
        String status = now.toLocalTime().isAfter(officeTime) ? "LATE" : "PRESENT";

        Attendance attendance = new Attendance();
        attendance.setUserId(user.getId());
        attendance.setCheckIn(now);
        attendance.setStatus(status);

        attendanceRepository.save(attendance);

        return "Checked In Successfully (" + status + ")";
    }

    @Override
    public String checkOut(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        Attendance attendance = attendanceRepository
                .findByUserIdAndCheckOutIsNull(user.getId())
                .orElseThrow(() -> new CustomException("No active check-in"));

        attendance.setCheckOut(LocalDateTime.now());

        attendanceRepository.save(attendance);

        return "Checked Out Successfully";
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAllByOrderByCheckInDesc();
    }

    @Override
    public AttendanceResponse getAttendanceByDate(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Attendance> list = attendanceRepository.findByCheckInBetween(start, end);

        List<AttendanceData> dataList = list.stream().map(a -> {
            AttendanceData d = new AttendanceData();
            d.setUserId(a.getUserId());
            d.setCheckIn(a.getCheckIn() != null ? a.getCheckIn().toLocalTime().toString() : null);
            d.setCheckOut(a.getCheckOut() != null ? a.getCheckOut().toLocalTime().toString() : null);
            d.setStatus(a.getStatus());
            return d;
        }).toList();

        AttendanceResponse response = new AttendanceResponse();
        response.setDate(date.toString());
        response.setTotalRecords(dataList.size());
        response.setData(dataList);

        return response;
    }

    @Override
    public DashboardResponse getDashboardStats(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(23, 59, 59);

        List<Attendance> list = attendanceRepository.findByCheckInBetween(start, end);

        int present = 0;
        int late = 0;

        for (Attendance a : list) {
            if ("PRESENT".equals(a.getStatus())) present++;
            else if ("LATE".equals(a.getStatus())) late++;
        }

        int total = userRepository.findAll().size();
        int absent = total - (present + late);

        DashboardResponse response = new DashboardResponse();
        response.setTotal(total);
        response.setPresent(present);
        response.setLate(late);
        response.setAbsent(absent);

        return response;
    }
}