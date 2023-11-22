package com.example.se_attendance.controller;

import com.example.se_attendance.Admin;
import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {
    @GetMapping("/admin/login")
    public String adminPage() {
        return "login";
    }


    private final MemberService memberService;

    @GetMapping("/html/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/html/mem_manage")
    public String memPage() {
        return "mem_manage";
    }

    @GetMapping("/admin/login")
    public Object loginAdmin(@RequestBody HashMap<String, String> loginInput) {

        return "message : "
                + ((Admin.isAdmin(loginInput.get("adminId"),
                loginInput.get("adminPassword"))) ?
                "로그인에 성공하였습니다." : "아이디 또는 비밀번호가 일치하지 않습니다." );

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("message", (Admin.isAdmin(loginInput.get("adminID"),
//                loginInput.get("adminPassword"))) ?
//                "로그인에 성공하였습니다." : "아이디 또는 비밀번호가 일치하지 않습니다." );

//        return jsonObject;

    }

    //DB에 저장된 회원 정보 전부 가져오기
    @GetMapping("/admin/info")
    public ResponseEntity<List<MemberDTO.MemberName>> getAllMember(){
        List<MemberDTO.MemberName> members = memberService.getAllMember();
        if (members==null) {
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return ResponseEntity.ok().body(memberService.getAllMember());
    }

    //특정 회원 정보 상세 조회
    @GetMapping("/admin/{memberId}/detail")
    public MemberDTO.Memberdto getMemberDetail(@PathVariable String memberId){
        MemberDTO.Memberdto memberDto = memberService.findById(memberId);
        if (memberDto==null){
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return memberDto;
    }

    //회원 정보 수정 (Web)
    @PutMapping("/admin/{memberId}/edit")
    public String updateMemberInfo(@PathVariable String memberId, @ModelAttribute MemberDTO.Memberdto memberDto){
        memberService.update(memberDto);
        String result = "회원 정보 수정에 성공 했습니다.";
        return "message : "+result;
    }

    //특정 회원 정보 삭제
    @DeleteMapping("/admin/{memberId}/delete")
    public Object deleteMember(@PathVariable String memberId){
        memberService.deleteById(memberId);

        return "memberId : "+memberId;
    }
}
