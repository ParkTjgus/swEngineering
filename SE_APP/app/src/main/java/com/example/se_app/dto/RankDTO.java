package com.example.se_app.dto;

public class RankDTO {
    /* 랭킹보기 */
    public static class RankResponse {
        private String memberId;    //학번
        private String memberName;  //이름
        private String memberMajor; //학과
        private int totalRecordTime; //출석시간
        private String message; //메세지

        //getter
        public String getMemberId() { return memberId; }
        public String getMemberName() { return memberName; }
        public String getMemberMajor() { return memberMajor; }
        public int getTotalRecordTime() { return totalRecordTime; }
        public String getMessage() { return message; }
    }

}