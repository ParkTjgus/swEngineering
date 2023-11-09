package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.domain.entity.StudyGoalEntity;
import com.example.se_attendance.repository.StudyGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyGoalService {

    private final StudyGoalRepository studyGoalRepository;

    //목표 시간 설정
    public void setStudyGoal(StudyGoalDTO.SetStudyGoal studyGoalDto) {
        StudyGoalEntity studyGoal = StudyGoalEntity.builder()
                .studyGoal(studyGoalDto.getStudyGoal())
                .build();
        studyGoalRepository.save(studyGoal);
    }
}
