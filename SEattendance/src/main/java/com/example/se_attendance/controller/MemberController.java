package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.service.MemberService;
import com.example.se_attendance.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    // 회원가입 (app)
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(
                                          @RequestBody MemberDTO.MemberSignUpDto memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return ResponseUtil.successResponse("회원가입에 성공했습니다.");
    }

    // 로그인 (app)
    @PostMapping("/login")
    public ResponseEntity<MemberDTO.LoginResponse> login(@RequestBody MemberDTO.MemberLoginDto memberLoginDto) {
        String token = memberService.login(memberLoginDto);
        return ResponseEntity.ok().body(MemberDTO.LoginResponse.builder()
                        .token(token)
                .build());
    }

    // 회원 정보 조회 (app)
    @GetMapping("/mypage")
    @ResponseBody
    public ResponseEntity<MemberDTO.Memberdto> getMyInfo(Authentication authentication) {
        return ResponseEntity.ok().body(memberService.findUser(authentication.getName()));
    }

    // 회원 정보 수정 (app)
    @PutMapping("/mypage")
    public ResponseEntity<?> updateMyInfo(Authentication authentication, @RequestBody MemberDTO.Memberdto user){
        memberService.updateMyInfo(authentication.getName(), user);
        return ResponseUtil.successResponse("회원 정보가 수정되었습니다.");
    }


}
