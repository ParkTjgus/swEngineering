package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.service.MemberService;
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
    public ResponseEntity<String> signUp(
                                          @RequestBody MemberDTO.MemberSignUpDto memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return new ResponseEntity<>("회원가입에 성공했습니다.", HttpStatus.OK);
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
    public MemberDTO.Memberdto getMyInfo(Authentication authentication) {
        return memberService.findUser(authentication.getName());
    }

    // 회원 정보 수정 (app)
    @PutMapping("/mypage")
    public ResponseEntity<String> updateMyInfo(Authentication authentication, @RequestBody MemberDTO.Memberdto user){
        memberService.updateMyInfo(authentication.getName(), user);
        return new ResponseEntity<>("회원 정보가 수정되었습니다.", HttpStatus.OK);
    }

    //DB에 저장된 회원 정보 전부 가져오기
    @GetMapping("/info")
    public ResponseEntity<List<MemberDTO.MemberName>> getAllMember(){
        List<MemberDTO.MemberName> members = memberService.getAllMember();
        if (members==null) {
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return ResponseEntity.ok().body(memberService.getAllMember());
    }

    //특정 회원 정보 상세 조회
    @GetMapping("/{memberId}/detail")
    public MemberDTO.Memberdto getMemberDetail(@PathVariable String memberId){
        MemberDTO.Memberdto memberDto = memberService.findById(memberId);
        if (memberDto==null){
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }
        return memberDto;
    }

    //회원 정보 수정 (Web)
    @PutMapping("/{memberId}/edit")
    public String updateMemberInfo(@PathVariable String memberId, @ModelAttribute MemberDTO.Memberdto memberDto){
        memberService.update(memberDto);
        String result = "회원 정보 수정에 성공 했습니다.";
        return "message : "+result;
    }

    //특정 회원 정보 삭제
    @DeleteMapping("/{memberId}/delete")
    public Object deleteMember(@PathVariable String memberId){
        memberService.deleteById(memberId);

        return "memberId : "+memberId;
    }
}
