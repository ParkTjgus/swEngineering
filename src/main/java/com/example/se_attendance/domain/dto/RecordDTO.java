package com.example.se_attendance.domain.dto;

import lombok.Getter;

public class RecordDTO {

    @Getter
    public static class RecordRequest {
        private double userLatitude;
        private double userLongitude;
    }
}
