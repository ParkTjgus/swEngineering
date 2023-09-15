package com.example.se_attendance.controller;


import com.example.se_attendance.domain.dto.TestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TestController {

    // 아무것도 없이 Get 해보기
    @GetMapping("/")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok().body("정상적으로 접근했습니다.");
    }

    // 메시지 Post 해보기
    @PostMapping("/")
    public ResponseEntity<String> postTest(@RequestBody TestDTO.TestRequest dto){
        String message = dto.getMessage();

        return ResponseEntity.ok().body(message);
    }
}
