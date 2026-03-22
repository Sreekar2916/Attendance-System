package com.company.attendance.service.impl;

import com.company.attendance.entity.Attendance;
import com.company.attendance.entity.User;
import com.company.attendance.repository.AttendanceRepository;
import com.company.attendance.repository.UserRepository;
import com.company.attendance.service.PdfService;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    @Override
    public void generateAttendancePdf(HttpServletResponse response) {

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=attendance.pdf");

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 🔹 Title
            Paragraph title = new Paragraph("Employee Attendance Report")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER);

            document.add(title);
            document.add(new Paragraph("\n"));

            // 🔹 Table with 4 columns
            Table table = new Table(4);

            table.addHeaderCell("Employee Name");
            table.addHeaderCell("Check-In");
            table.addHeaderCell("Check-Out");
            table.addHeaderCell("Status");

            List<Attendance> list = attendanceRepository.findAll();

            for (Attendance a : list) {

                User user = userRepository.findById(a.getUserId()).orElse(null);

                String name = (user != null) ? user.getName() : "Unknown";

                table.addCell(name);
                table.addCell(a.getCheckIn() != null ? a.getCheckIn().toLocalTime().toString() : "-");
                table.addCell(a.getCheckOut() != null ? a.getCheckOut().toLocalTime().toString() : "-");
                table.addCell(a.getStatus());
            }

            document.add(table);
            document.close();

        } catch (IOException e) {
            throw new RuntimeException("Error generating PDF");
        }
    }



    @Override
    public void generateAttendancePdfByDate(HttpServletResponse response, LocalDate date) {

        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=attendance_" + date + ".pdf");

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // 🔹 Title
            document.add(new Paragraph("Attendance Report for " + date)
                    .setBold()
                    .setFontSize(16));

            document.add(new Paragraph("\n"));

            // 🔹 Table
            Table table = new Table(4);
            table.addHeaderCell("Employee Name");
            table.addHeaderCell("Check-In");
            table.addHeaderCell("Check-Out");
            table.addHeaderCell("Status");

            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.atTime(23, 59, 59);

            List<Attendance> list = attendanceRepository.findByCheckInBetween(start, end);

            for (Attendance a : list) {

                User user = userRepository.findById(a.getUserId()).orElse(null);
                String name = (user != null) ? user.getName() : "Unknown";

                table.addCell(name);
                table.addCell(a.getCheckIn() != null ? a.getCheckIn().toLocalTime().toString() : "-");
                table.addCell(a.getCheckOut() != null ? a.getCheckOut().toLocalTime().toString() : "-");
                table.addCell(a.getStatus());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating date PDF");
        }
    }
}