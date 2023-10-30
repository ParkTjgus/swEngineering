package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.NoticeDTO;
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

    //공지사항 생성
    //생성한 공지사항의 id값이 리턴됨
    @PostMapping("")
    public void createNotice(@RequestBody NoticeDTO.NoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
    }

    //공지사항 조회
    @GetMapping("/{id}")
    public NoticeDTO.NoticeDto findNotice(@PathVariable Long id){
        return noticeService.findNotice(id);
    }

    //공지사항 목록 조회 추가!!
    @GetMapping("")
    public List<NoticeDTO.NoticeDto> findAllNotice() { return noticeService.findAll(); }


    //공지사항 수정
    //생성시간, 수정시간도 뜨게 dto 변수 추가 후 수정하기?
    @PatchMapping("/{id}")
    public Long updateNotice(@PathVariable Long id, @RequestBody NoticeDTO.NoticeDto noticeDto){
        return noticeService.updateNotice(id, noticeDto);
    }

    //공지사항 삭제
    @DeleteMapping ("/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
        return new ResponseEntity<>("delete", HttpStatus.OK);
    }
}
