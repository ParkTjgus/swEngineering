package com.example.se_attendance.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class TestDTO {

    @Getter
    public static class TestRequest {
        private String message;
    }

    @Getter
    @Builder
    public static class TestQueryResponse {
        private String name;
        private String message;
    }

    @Getter
    @AllArgsConstructor
    public static class TestQueryRequest {
        private String name;
        private String message;
    }

    @Getter
    public static class TestJwtRequest {
        private String name;
    }

    @Getter
    @Builder
    public static class TestJwtResponse {
        private String token;
    }
}
