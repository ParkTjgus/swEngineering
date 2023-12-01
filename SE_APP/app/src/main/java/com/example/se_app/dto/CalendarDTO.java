package com.example.se_app.dto;

public class CalendarDTO {
    /* 목표시간 응답 */
    public static class GoalResponse {
        private int studyGoal; //목표시간(초)

        //getter
        public int getStudyGoal() { return studyGoal; }
    }

    /* 출석 기록 응답 */
    public static class TimeResponse {
        private int recordTime; //출석시간(초)

        //getter
        public int getRecordTime() { return recordTime; }
    }

}
