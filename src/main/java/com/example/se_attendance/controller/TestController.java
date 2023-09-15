package com.example.se_attendance.controller;


import com.example.se_attendance.domain.dto.TestDTO;
import com.example.se_attendance.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // 아무것도 없이 Get 해보기
    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok().body("정상적으로 접근했습니다.");
    }

    // 메시지 Post 해보기
    @PostMapping("/test")
    public ResponseEntity<String> postTest(@RequestBody TestDTO.TestRequest dto){
        String message = dto.getMessage();

        return ResponseEntity.ok().body(message);
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
}
