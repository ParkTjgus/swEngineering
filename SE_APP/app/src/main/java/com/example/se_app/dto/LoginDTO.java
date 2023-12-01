package com.example.se_app.dto;

public class LoginDTO {
    /* 로그인 요청 */
    public static class LoginRequest {
        private String memberId; //아이디
        private String memberPw; //비밀번호

        public LoginRequest(String memberId, String memberPw) {
            this.memberId = memberId;
            this.memberPw = memberPw;
        }

        //setter
        public void setMemberId(String memberId) { this.memberId = memberId; }
        public void setMemberPw(String memberPw) { this.memberPw = memberPw; }
    }

    /* 로그인 응답 */
    public static class LoginResponse {
        private String token;
        private String message;

        //getter
        public String getToken() { return token; }
        public String getMessage() { return message; }
    }
}
