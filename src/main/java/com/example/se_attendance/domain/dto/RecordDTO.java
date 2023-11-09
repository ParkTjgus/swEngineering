package com.example.se_attendance.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class RecordDTO {

    // 기록 하기
    @Getter
    public static class RecordRequest {
        private double userLatitude;
        private double userLongitude;
    }

    // 당일 기록 가져오기
    @Getter
    @Builder
    public static class TodayRecordResponse {
        private int recordTime;
    }

    @Getter
    public static class stopRequest {
        private int recordTime;
        private double userLatitude;
        private double userLongitude;
    }

    @Getter
    public static class sendLocation {
        private int recordTime;
        private double userLatitude;
        private double userLongitude;
    }

    @Getter
    @Builder
    public static class MyRecord {
        private int recordTime;
        private LocalDate recordDate;
    }
}
