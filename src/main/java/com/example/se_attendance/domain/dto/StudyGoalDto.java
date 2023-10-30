package com.example.se_attendance.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyGoalDto {
    private Long id;
    private int studyGoal;
}
