package com.example.se_attendance.controller;

import com.example.se_attendance.Admin;
import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.service.MemberService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    //login 페이지 반환 코드
    @GetMapping("/html/login")
    public String loginPage() {
        return "login";
    }

    //회원 관리 페이지 반환 코드
    @GetMapping("/html/mem_manage")
    public String memManagePage() {
        return "mem_manage";
    }

    //회원 상세 정보 페이지 반환 코드
    @GetMapping("/html/mem_detail")
    public String memDetailPage(){
        return "mem_detail";
    }

    //admin 로그인 판별
    @PostMapping("/admin/login")
    public ResponseEntity<?> loginAdmin(@RequestBody HashMap<String, String> loginInput) {

        if ((Admin.isAdmin(loginInput.get("adminId"), loginInput.get("adminPassword"))))
            return ResponseUtil.successResponse("로그인에 성공하였습니다.");
        else
            throw new AppException(ErrorCode.NOT_FOUND, "아이디 또는 비밀번호가 일치하지 않습니다.");

    }

    //DB에 저장된 회원 정보 전부 가져오기
    //추가 코드
    @GetMapping("/admin/memInfo")
    public ResponseEntity<List<MemberDTO.rankMember>> getAllMember(){
        List<MemberDTO.rankMember> members = memberService.getAllMember();
        if (members==null) {
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return ResponseEntity.ok().body(members);
    }

    //특정 회원 정보 상세 조회
    @GetMapping("/admin/{memberId}/detail")
    public ResponseEntity<MemberDTO.Memberdto> getMemberDetail(@PathVariable String memberId){
        MemberDTO.Memberdto memberDto = memberService.findById(memberId);
        if (memberDto==null){
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return ResponseEntity.ok().body(memberDto);
    }

    //회원 정보 수정 (Web)
    @PutMapping("/admin/{memberId}/edit")
    public ResponseEntity<?> updateMemberInfo(@PathVariable String memberId, @ModelAttribute MemberDTO.Memberdto memberDto){
        memberService.update(memberDto);
        return ResponseUtil.successResponse("회원 정보 수정에 성공 했습니다.");
    }

    //특정 회원 정보 삭제
    @DeleteMapping("/admin/{memberId}/delete")
    public ResponseEntity<?> deleteMember(@PathVariable String memberId){
        memberService.deleteById(memberId);
        return ResponseUtil.successResponse(memberId);
    }
}

