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

    // ✅ CHECK-IN
    @PostMapping("/check-in")
    public String checkIn(HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");
        String ip = request.getRemoteAddr();

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return attendanceService.checkIn(email, ip);
    }

    // ✅ CHECK-OUT
    @PostMapping("/check-out")
    public String checkOut(HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return attendanceService.checkOut(email);
    }

    // ✅ GET ALL (TEMP: NO ADMIN CHECK)
    @GetMapping("/all")
    public List<Attendance> getAllAttendance(HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        return attendanceService.getAllAttendance();
    }

    // ✅ PDF DOWNLOAD (TEMP: NO ADMIN CHECK)
    @GetMapping("/pdf")
    public void downloadPdf(HttpServletRequest request,
                            HttpServletResponse response) {

        String email = (String) request.getAttribute("userEmail");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        pdfService.generateAttendancePdf(response);
    }

    // ✅ PDF BY DATE
    @GetMapping("/pdf/{date}")
    public void downloadPdfByDate(@PathVariable String date,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        String email = (String) request.getAttribute("userEmail");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        LocalDate localDate = LocalDate.parse(date);

        pdfService.generateAttendancePdfByDate(response, localDate);
    }

    // ✅ DASHBOARD (TEMP: NO ADMIN CHECK)
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(@RequestParam String date,
                                          HttpServletRequest request) {

        String email = (String) request.getAttribute("userEmail");

        if (email == null) {
            throw new RuntimeException("Unauthorized");
        }

        LocalDate localDate = LocalDate.parse(date);

        return attendanceService.getDashboardStats(localDate);
    }
}