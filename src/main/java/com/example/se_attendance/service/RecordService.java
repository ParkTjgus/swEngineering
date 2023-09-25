package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.domain.entity.RecordEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.RecordRepository;
import com.example.se_attendance.utils.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        RecordEntity recordEntity = RecordEntity.builder()
                .userId(JwtUtil.getUserIdFromToken())
                .build();
        recordRepository.save(recordEntity);
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

    // 기록 중단하기
    public void stopRecord(RecordDTO.stopRequest dto) {

        // 위치 가져오기
        double latitude = dto.getUserLatitude();
        double longitude = dto.getUserLongitude();

        // 위치 확인 로직 (추후 범위 지정 해줘야함)
        if(latitude > 10 && longitude > 10) {
            throw new AppException(ErrorCode.INVALID_INPUT, "동아리방에서 출석해 주세요");
        }

        // userId 가져오기
        String userId = JwtUtil.getUserIdFromToken();

        // 오늘 날짜 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 날짜의 00:00:00
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 내일 날짜의 00:00:00

        // 같은 userId를 가진 RecordEntity 찾기
        RecordEntity existingRecord = recordRepository.findByUserIdToday(userId, startOfDay, endOfDay).orElse(null); // findByUserId는 RecordRepository에 구현해야 할 메소드입니다.

        if (existingRecord != null) {
            // 이미 존재하는 레코드 업데이트
            existingRecord.setRecordTime(dto.getRecordTime());
            recordRepository.save(existingRecord);
        } else {
            throw  new AppException(ErrorCode.INVALID_INPUT, "해당 사용자는 없습니다.");
        }
    }
}
