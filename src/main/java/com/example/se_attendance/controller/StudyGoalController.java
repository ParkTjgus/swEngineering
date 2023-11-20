package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.service.StudyGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudyGoalController {
    private final StudyGoalService studyGoalService;

    //목표시간 설정하기
    @PostMapping("/studygoal")
    public ResponseEntity<String> setStudyGoal(@RequestBody StudyGoalDTO.SetStudyGoal studyGoalDto){
        studyGoalService.setStudyGoal(studyGoalDto);
        return new ResponseEntity<>("목표시간이 설정되었습니다.", HttpStatus.OK);
    }

    // 목표 시간 달성한 멤버 출력하기 (web)
    @GetMapping("/record")
    public ResponseEntity<List<MemberDTO.rankMember>> showRecord(){
        return ResponseEntity.ok().body(studyGoalService.findMembers());
    }
}
