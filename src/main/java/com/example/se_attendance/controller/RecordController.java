package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.service.RecordService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;
    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody RecordDTO.RecordRequest dto) {
        recordService.record(dto);

        return ResponseUtil.successResponse("기록 시작합니다.");
    }
}
