package com.example.se_attendance.controller;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp( //Exception 안넣어주니까 오류 나던데 왜지? @NotNull과 관련?
                                          @RequestBody MemberDTO.MemberSignUpDto memberSignUpDto) throws Exception {
        memberService.signUp(memberSignUpDto);
        return new ResponseEntity<>("회원가입에 성공했습니다.", HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login( //얘는 Exception 없어도 오류 안남? String으로 왜 작동함? service가 리턴하는게 string이라서?
                                         @RequestBody MemberDTO.MemberLoginDto memberLoginDto) {
        return ResponseEntity.ok(memberService.login(memberLoginDto));
    }

    @GetMapping("/mypage")
    @ResponseBody
    public MemberDTO.Memberdto getMyInfo() { //authentication이 뭐임? 이것만 인자로 넘겨줘도 알아서 찾을 수 있는거임?
        return memberService.findUser();
    }

    @PatchMapping("/mypage")
    public ResponseEntity<String> updateMyInfo(@RequestBody MemberDTO.Memberdto user){ //얘도 AuthenticationPrincipal 어노테이션 쓰면 되는걸까? 일단 entity를 매개변수로 넘겨주긴 해야함
        memberService.updateMyInfo(user);
        return new ResponseEntity<>("회원 정보 수정 완", HttpStatus.OK);
    }

    //DB에 저장된 회원 정보 전부 가져오기
    @GetMapping("/info")
    public ResponseEntity<List<MemberDTO.MemberName>> getAllMember(){
        return ResponseEntity.ok().body(memberService.getAllMember());
    }

    //특정 회원 정보 상세 조회
    @GetMapping("/{memberId}/detail")
    public MemberDTO.Memberdto getMemberDetail(@PathVariable String memberId){
        MemberDTO.Memberdto memberDto = memberService.findById(memberId);
        if (memberDto==null){
            assert true;
        }
        return memberDto;
    }

    @PutMapping("/{memberId}/edit")
    public String updateMemberInfo(@PathVariable String memberId, @ModelAttribute MemberDTO.Memberdto memberDto){
        memberService.update(memberDto);
        String result = "회원 정보 수정에 성공 했습니다.";
        //String reulst = "저장된 회원 정보가 없습니다.";
        return "message : "+result;
    }

    //특정 회원 정보 삭제
    @DeleteMapping("/{memberId}/delete")
    public Object deleteMember(@PathVariable String memberId){
        memberService.deleteById(memberId);

        return "memberId : "+memberId;
    }
}
