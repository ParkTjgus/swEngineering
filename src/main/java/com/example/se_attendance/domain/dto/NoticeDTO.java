package com.example.se_attendance.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NoticeDTO {

   // 공지사항의 모든 정보
    @Getter
    @Builder
    public static class GetNotice {
        private String noticeContent;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }

    @Getter
    @Builder
    public static class NoticeDto {
        private Long id;
        private String noticeContent;
        private LocalDateTime createTime;
    }
}
