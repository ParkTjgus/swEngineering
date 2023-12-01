package com.example.se_app.dto;

public class RegisterDTO {
    /* 회원가입 요청 */
    public static class RegisterRequest {
        private String memberId; //학번(아이디)
        private String memberPw; //비밀번호
        private String memberName; //이름
        private String memberMajor; //전공(학과)
        private String memberState; //상태(휴학/재학/졸업)
        private String memberBirth; //생년월일

        public RegisterRequest(String memberId, String memberPw, String memberName, String memberMajor, String memberState, String memberBirth) {
            this.memberId = memberId;
            this.memberPw = memberPw;
            this.memberName = memberName;
            this.memberMajor = memberMajor;
            this.memberState = memberState;
            this.memberBirth = memberBirth;
        }

        //setter
        public void setMemberId(String memberId) { this.memberId = memberId; }
        public void setMemberPw(String memberPw) { this.memberPw = memberPw; }
        public void setMemberName(String memberName) { this.memberName = memberName; }
        public void setMemberMajor(String memberMajor) { this.memberMajor = memberMajor; }
        public void setMemberState(String memberState) { this.memberState = memberState; }
        public void setMemberBirth(String memberBirth) { this.memberBirth = memberBirth; }
    }

    /* 회원가입 응답 */
    public static class RegisterResponse {
        private String message;

        //getter
        public String getMessage() { return message; }
    }
}
