package com.example.se_app.dto;

public class EditDTO {
    /* 회원 정보 수정 요청 */
    public static class EditRequest {
        private String memberPw; //비밀번호
        private String memberName; //이름
        private String memberMajor; //전공(학과)
        private String memberState; //상태(휴학/재학/졸업)
        private String memberBirth; //생년월일

        public EditRequest(String memberPw, String memberName, String memberMajor, String memberState, String memberBirth) {
            this.memberPw = memberPw;
            this.memberName = memberName;
            this.memberMajor = memberMajor;
            this.memberState = memberState;
            this.memberBirth = memberBirth;
        }

        //setter
        public void setMemberPw(String memberPw) { this.memberPw = memberPw; }
        public void setMemberName(String memberName) { this.memberName = memberName; }
        public void setMemberMajor(String memberMajor) { this.memberMajor = memberMajor; }
        public void setMemberState(String memberState) { this.memberState = memberState; }
        public void setMemberBirth(String memberBirth) { this.memberBirth = memberBirth; }
    }

    /* 회원 정보 수정 응답 */
    public static class EditResponse {
        private String message; //메세지

        //getter
        public String getMessage() { return message; }
    }
}
