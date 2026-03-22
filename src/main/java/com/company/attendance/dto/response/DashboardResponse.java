package com.company.attendance.dto.response;

import lombok.Data;

@Data
public class DashboardResponse {

    private int total;
    private int present;
    private int late;
    private int absent;
}