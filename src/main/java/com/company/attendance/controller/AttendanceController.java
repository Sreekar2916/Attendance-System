package com.company.attendance.controller;

import com.company.attendance.dto.response.DashboardResponse;
import com.company.attendance.entity.Attendance;
import com.company.attendance.service.AttendanceService;
import com.company.attendance.service.PdfService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final PdfService pdfService;

    // ✅ CHECK-IN (NO userId)
    @PostMapping("/check-in")
    public String checkIn(HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");
        String ip = request.getRemoteAddr();

        if (email == null) {
            throw new RuntimeException("Unauthorized: No user found in token");
        }

        return attendanceService.checkIn(email, ip);
    }

    // ✅ CHECK-OUT
    @PostMapping("/check-out")
    public String checkOut(HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");

        return attendanceService.checkOut(email);
    }

    @GetMapping("/all")
    public List<Attendance> getAllAttendance(HttpServletRequest request) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN");
        }

        return attendanceService.getAllAttendance();
    }

    @GetMapping("/pdf")
    public void downloadPdf(HttpServletRequest request,
                            HttpServletResponse response) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN");
        }

        pdfService.generateAttendancePdf(response);
    }

    @GetMapping("/pdf/{date}")
    public void downloadPdfByDate(@PathVariable String date,   // ✅ CORRECT
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN");
        }

        LocalDate localDate = LocalDate.parse(date);

        pdfService.generateAttendancePdfByDate(response, localDate);
    }

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(@RequestParam String date,
                                          HttpServletRequest request) {

        String role = (String) request.getAttribute("userRole");

        if (!"ADMIN".equals(role)) {
            throw new RuntimeException("Access Denied: Only ADMIN");
        }

        LocalDate localDate = LocalDate.parse(date);

        return attendanceService.getDashboardStats(localDate);
    }
}