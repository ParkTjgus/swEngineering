package com.example.se_attendance.controller;

import com.example.se_attendance.Admin;
import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.service.StudyGoalService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyGoalController {
    private final StudyGoalService studyGoalService;

    //goalTime 페이지 반환 코드
    @GetMapping("/html/goal_time")
    public String goalTimePage() {
        return "goalTime";
    }

    //목표시간 설정하기
    @PostMapping("/admin/studygoal")
    public ResponseEntity<?> setStudyGoal(@RequestBody StudyGoalDTO.SetStudyGoal studyGoalDto){
        studyGoalService.setStudyGoal(studyGoalDto);
        return ResponseUtil.successResponse("목표시간이 설정되었습니다.");
    }

    // 목표 시간 달성한 멤버 출력하기 (web)
    @GetMapping("/admin/record")
    public ResponseEntity<List<MemberDTO.rankMember>> showRecord(){
        return ResponseEntity.ok().body(studyGoalService.findMembers());
    }
}
