package com.example.se_attendance.controller;


import com.example.se_attendance.domain.dto.TestDTO;
import com.example.se_attendance.service.TestService;
import com.example.se_attendance.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // 아무것도 없이 Get 해보기
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> getTest() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "정상적으로 접근했습니다.");

        return ResponseEntity.ok().body(response);
    }

    // 메시지 Post 해보기
    @PostMapping("/test")
    public ResponseEntity<Map<String, String>> postTest(@RequestBody TestDTO.TestRequest dto){
        String message = dto.getMessage();

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok().body(response);
    }

    // 쿼리를 이용해 Get 해보기
    @GetMapping("/test/query")
    public ResponseEntity<TestDTO.TestQueryResponse> getTestQuery(@ModelAttribute TestDTO.TestQueryRequest dto) {
        TestDTO.TestQueryResponse testQuery = testService.getTestQuery(dto);

        return ResponseEntity.ok().body(testQuery);
    }

    // Jwt 받아오기
    @PostMapping("/test/jwt")
    public ResponseEntity<TestDTO.TestJwtResponse> returnJwt(@RequestBody TestDTO.TestJwtRequest dto) {
        return ResponseEntity.ok().body(testService.getJwt(dto));
    }

    // Jwt 사용해보기
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> useJwt() {
        String name = JwtUtil.getUserIdFromToken(); // 토큰에서 이름 가져오기

        Map<String, String> response = new HashMap<>();
        response.put("message", name + "님 수고하셨습니다.");

        return ResponseEntity.ok().body(response);
    }
}
