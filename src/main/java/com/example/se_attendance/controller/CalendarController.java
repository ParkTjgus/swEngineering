package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.service.NoticeService;
import com.example.se_attendance.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final NoticeService noticeService;
    private final RecordService recordService;

    @GetMapping("/notice/now")
    public ResponseEntity<NoticeDTO.GetNotice> getNotice() {
        return ResponseEntity.ok().body(noticeService.getNotice());
    }

    @GetMapping("/myrecord")
    public ResponseEntity<List<RecordDTO.MyRecord>> getMyRecord(@RequestParam String month) {
        return ResponseEntity.ok().body(recordService.getMyRecord(month));
    }

    @GetMapping("/studygoal")
    public ResponseEntity<StudyGoalDTO.GetStudyGoal> getStudyGoal(@RequestParam String month) {
        return ResponseEntity.ok().body(recordService.getStudyGoal(month));
    }
}
