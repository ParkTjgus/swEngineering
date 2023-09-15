package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.TestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {

    // 쿼리 테스트 서비스
    public TestDTO.TestQueryResponse getTestQuery(TestDTO.TestQueryRequest dto) {
        return TestDTO.TestQueryResponse.builder()
                .name(dto.getName())
                .message(dto.getMessage())
                .build();
    }
}
