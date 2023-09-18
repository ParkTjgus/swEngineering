package com.example.se_attendance.domain.dto;

import lombok.Builder;
import lombok.Getter;

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
}
