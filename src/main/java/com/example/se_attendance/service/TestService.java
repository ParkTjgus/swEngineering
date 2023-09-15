package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.TestDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

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
}
