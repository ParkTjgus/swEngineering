package com.example.se_attendance.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok().body("정상적으로 접근했습니다.");
    }
}
