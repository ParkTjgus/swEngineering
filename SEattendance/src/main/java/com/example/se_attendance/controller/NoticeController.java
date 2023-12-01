package com.example.se_attendance.controller;

import com.example.se_attendance.Admin;
import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.domain.entity.NoticeEntity;
import com.example.se_attendance.service.NoticeService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class NoticeController {

    private final NoticeService noticeService;

    //공지사항 등록 페이지 반환 코드
    @GetMapping("/html/info_reg")
    public String infoRegPage() {
        return "info_reg";
    }

    //공지사항 상세 조회 페이지 반환 코드
    @GetMapping("/html/info_detail")
    public String infoDetailPage() {
        return "info_detail";
    }

    //info_manage 페이지 반환 코드
    @GetMapping("/html/info_manage")
    public String infoManagePage(){
        return "info_manage";
    }

   //공지사항 생성 (web)
    @PostMapping("/admin/notice")
    public ResponseEntity<?> createNotice(@RequestBody NoticeDTO.NoticeDto noticeDto){
        noticeService.createNotice(noticeDto);
        return ResponseUtil.successResponse("공지사항이 등록되었습니다.");

    }

    //공지사항 조회 (web)
    @GetMapping("/admin/notice/{id}")
    public ResponseEntity<NoticeDTO.NoticeDto> findNotice(@PathVariable Long id){
        return ResponseEntity.ok().body(noticeService.findNotice(id));
    }

    //공지사항 목록 조회 (web)
    @GetMapping("/admin/notice")
    public ResponseEntity<List<NoticeEntity>> findAllNotice() {
        return ResponseEntity.ok().body(noticeService.findAll());
    }

    //공지사항 삭제 (web)
    @DeleteMapping ("/admin/notice/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable Long id){
        noticeService.deleteNotice(id);
        return ResponseUtil.successResponse("공지사항이 삭제되었습니다.");
    }
}
