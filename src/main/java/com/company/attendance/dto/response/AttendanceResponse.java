package com.company.attendance.dto.response;

import lombok.Data;
import java.util.List;

@Data
public class AttendanceResponse {

    private String date;
    private int totalRecords;   // ✅ THIS WAS MISSING
    private List<AttendanceData> data;
}