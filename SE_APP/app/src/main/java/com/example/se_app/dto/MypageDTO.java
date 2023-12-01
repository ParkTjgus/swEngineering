package com.example.se_app.dto;

import java.time.LocalDateTime;

public class MypageDTO {
    /* 마이페이지 정보 조회 응답 */
    public static class MypageResponse {
        private String memberId; //학번(아이디)
        private String memberPw; //비밀번호
        private String memberName; //이름
        private String memberMajor; //전공(학과)
        private String memberState; //상태(휴학/재학/졸업)
        private String memberBirth; //생년월일
        private LocalDateTime createTime; //가입일

        //getter
        public String getMemberId() { return memberId; }
        public String getMemberPw() { return memberPw; }
        public String getMemberName() { return memberName; }
        public String getMemberMajor() { return memberMajor; }
        public String getMemberState() { return memberState; }
        public String getMemberBirth() { return memberBirth; }
        public LocalDateTime getCreateTime() { return createTime; }
    }
}
