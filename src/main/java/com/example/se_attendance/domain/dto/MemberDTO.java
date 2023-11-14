package com.example.se_attendance.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MemberDTO {

    @Data //Getter, Setter, ToString, EqualsAndHashCode, RequiredArgsConstructor 모두 포함
    @Builder
    @AllArgsConstructor
    public static class MemberSignUpDto {
        private String memberId;
        private String memberPw;
        private String memberName;
        private String memberMajor;
        private String memberState;
        private String memberBirth;
    }

    @Data
    @NoArgsConstructor
    public static class MemberLoginDto {
        private String memberId;
        private String memberPw;
    }

    @Builder
    @Getter //없으면 회원 정보 조회(mypage)할 때 406 오류남
    public static class Memberdto {
        private String memberId;

        private String memberPw;

        private String memberName;

        private String memberMajor;

        private String memberState;

        private String memberBirth;

        private LocalDateTime createTime;

    }

    @Builder
    @Getter
    public static class MemberName {
        private String memberId;
        private String memberName;
    }

    @Builder
    @Getter
    public static class rankMember{
        private String memberId;
        private String memberName;
        private String memberMajor;
        private int recordTime;
    }
}
