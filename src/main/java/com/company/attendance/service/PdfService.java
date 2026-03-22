package com.company.attendance.service;

import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDate;

public interface PdfService {

    void generateAttendancePdf(HttpServletResponse response);

    void generateAttendancePdfByDate(HttpServletResponse response, LocalDate date);
}