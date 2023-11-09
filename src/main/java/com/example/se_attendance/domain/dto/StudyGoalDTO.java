package com.example.se_attendance.domain.dto;

import lombok.Builder;
import lombok.Getter;

public class StudyGoalDTO {

    @Getter
    @Builder
    public static class GetStudyGoal {
        private int studyGoal;
    }

    @Getter
    @Builder
    public static class SetStudyGoal {
        private Long id;
        private int studyGoal;
    }
}
