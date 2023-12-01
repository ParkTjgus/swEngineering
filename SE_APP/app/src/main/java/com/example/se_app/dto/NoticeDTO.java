package com.example.se_app.dto;

import java.time.LocalDateTime;

public class NoticeDTO {
    /* 공지사항 조회 응답 */
    public static class NoticeResponse {
        private String message; //메세지
        private String noticeContent; //공지사항 내용

        //getter
        public String getMessage() { return message; }
        public String getNoticeContent() { return noticeContent; }
    }
}
