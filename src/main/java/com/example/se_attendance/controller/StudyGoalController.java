package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.StudyGoalDto;
import com.example.se_attendance.service.StudyGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyGoalController {
    private final StudyGoalService studyGoalService;

    //목표시간 설정하기
    @PostMapping("/studygoal")
    public void setStudyGoal(@RequestBody StudyGoalDto studyGoalDto){
        studyGoalService.setStudyGoal(studyGoalDto);
    }
}
