package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.TestDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    @Value("${jwt.secret}")
    private String secretKey;

    // 쿼리 테스트 서비스
    public TestDTO.TestQueryResponse getTestQuery(TestDTO.TestQueryRequest dto) {
        String name = dto.getName();
        String message = dto.getMessage();

        // null 또는 공백 문자열 검사
        if (name == null || name.isBlank() || message == null || message.isBlank()) {
            throw new AppException(ErrorCode.INVALID_INPUT,"모두 입력해 주세요!!");
        }

        return TestDTO.TestQueryResponse.builder()
                .name(name)
                .message(message)
                .build();
    }

    // jwt 토큰 만들어서 리턴해주기
    public TestDTO.TestJwtResponse getJwt(TestDTO.TestJwtRequest dto) {
        String name = dto.getName();

        Long expireTimeMs = 1000* 60 * 60l; // 만기 시간 1시간 설정

        // null 또는 공백 문자열 검사
        if (name == null || name.isBlank()) {
            throw new AppException(ErrorCode.INVALID_INPUT,"이름 입력해 주세요!!");
        }

        return TestDTO.TestJwtResponse.builder()
                .token(JwtUtil.createToken(name, secretKey, expireTimeMs))
                .build();
    }
}
