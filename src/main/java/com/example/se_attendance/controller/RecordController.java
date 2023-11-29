package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.service.RecordService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    // 기록하기
    @PostMapping("/record")
    public ResponseEntity<?> record(@RequestBody RecordDTO.RecordRequest dto) {
        recordService.record(dto);

        return ResponseUtil.successResponse("기록 시작합니다.");
    }

    // 당일 기록 가져오기
    @GetMapping("/record/today")
    public ResponseEntity<RecordDTO.TodayRecordResponse> getRecord() {


        return ResponseEntity.ok().body(recordService.getRecord());
    }

    // 기록 중단
    @PostMapping("/record/stop")
    public ResponseEntity<Void> stopRecord(@RequestBody RecordDTO.stopRequest dto) {
        recordService.stopRecord(dto);

        return ResponseEntity.ok().build();
    }

    // 위치 보내기
    @PostMapping("/record/location")
    public ResponseEntity<Void> sendLocation(@RequestBody RecordDTO.sendLocation dto) {
        recordService.sendLocation(dto);

        return ResponseEntity.ok().build();
    }

//    @GetMapping("/rank/time/{month}")
//    public ResponseEntity<List<MemberDTO.rankMember>> getTimeTop5(@PathVariable String month){
//        return ResponseEntity.ok().body(recordService.findTimeTop5(month));
//    }
//
//    @GetMapping("/rank/day/{month}")
//    public ResponseEntity<List<MemberDTO.rankMember>> getDayTop5(@PathVariable String month){
//        return ResponseEntity.ok().body(recordService.findDayTop5(month));
//    }
}
