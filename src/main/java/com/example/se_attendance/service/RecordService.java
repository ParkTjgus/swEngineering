package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.RecordRepository;
import com.example.se_attendance.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    // 기록하기
    public void record(RecordDTO.RecordRequest dto) {

        double latitude = dto.getUserLatitude();
        double longitude = dto.getUserLongitude();
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        // 위치 확인 로직 (추후 범위 지정 해줘야 함)
        if(latitude > 10 && longitude > 10) {
            throw new AppException(ErrorCode.INVALID_INPUT, "동아리방에서 출석해 주세요");
        }
    }

    // 당일 기록 가져오기
    public RecordDTO.TodayRecordResponse getRecord() {

        // 토큰에서 학번 가져오기
        String userId = JwtUtil.getUserIdFromToken();
        System.out.println(userId);

        // 오늘 날짜 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 날짜의 00:00:00
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 내일 날짜의 00:00:00

        // DB에 userId가 없을 경우
        int recordTime = recordRepository.findRecordByUserIdToday(userId, startOfDay, endOfDay).orElse(0);

        return RecordDTO.TodayRecordResponse.builder()
                .recordTime(recordTime)
                .build();
    }
}
