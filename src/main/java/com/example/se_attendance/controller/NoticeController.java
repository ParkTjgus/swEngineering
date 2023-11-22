package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.domain.entity.NoticeEntity;
import com.example.se_attendance.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeService;

   //공지사항 생성 (web)
    @PostMapping("")
    public ResponseEntity<String> createNotice(@RequestBody NoticeDTO.NoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
        return new ResponseEntity<>("공지사항이 등록되었습니다.", HttpStatus.OK);
    }

    //공지사항 조회 (web)
    @GetMapping("/{id}")
    public NoticeDTO.NoticeDto findNotice(@PathVariable Long id){
        return noticeService.findNotice(id);
    }

    //공지사항 목록 조회 (web)
    @GetMapping("")
    public ResponseEntity<List<NoticeEntity>> findAllNotice() {
        return ResponseEntity.ok().body(noticeService.findAll());
    }

    //공지사항 삭제 (web)
    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
        return new ResponseEntity<>("공지사항이 삭제되었습니다.", HttpStatus.OK);
    }
}
