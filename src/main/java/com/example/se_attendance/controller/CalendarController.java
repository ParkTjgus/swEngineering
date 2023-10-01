package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    public ResponseEntity<NoticeDTO.GetNotice> getNotice() {
        return ResponseEntity.ok().body(noticeService.getNotice());
    }
}
