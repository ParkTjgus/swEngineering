package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.MemberDTO;
import com.example.se_attendance.domain.dto.RecordDTO;
import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.domain.entity.RecordEntity;
import com.example.se_attendance.domain.entity.StudyGoalEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.RecordRepository;
import com.example.se_attendance.repository.StudyGoalRepository;
import com.example.se_attendance.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final StudyGoalRepository studyGoalRepository;

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
//        RecordEntity recordEntity = RecordEntity.builder()
//                .memberEntity(JwtUtil.getUserIdFromToken())
//                .build();
//        recordRepository.save(recordEntity);
    }

    // 당일 기록 가져오기
    public RecordDTO.RecordTimeResponse getRecord() {

        // 토큰에서 학번 가져오기
        String userId = JwtUtil.getUserIdFromToken();
        System.out.println(userId);

        // 오늘 날짜 계산
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 날짜의 00:00:00
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 내일 날짜의 00:00:00

        // DB에 userId가 없을 경우
        int recordTime = recordRepository.findRecordByUserIdToday(userId, startOfDay, endOfDay).orElse(0);

        return RecordDTO.RecordTimeResponse.builder()
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
//            RecordEntity recordEntity = Recor
        }
    }

    // 위치 보내기
    public void sendLocation(RecordDTO.sendLocation dto) {

        // 위치 가져오기
        double latitude = dto.getUserLatitude();
        double longitude = dto.getUserLongitude();

        // 위치 확인 로직 (추후 범위 지정 해줘야함)
        if(latitude > 10 || longitude > 10) {
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

    // 출석한 날짜 받아오기
    public RecordDTO.RecordTimeResponse getMyRecord(String date) {
        String userId = JwtUtil.getUserIdFromToken();

        // 날짜 계산
        LocalDate today = LocalDate.parse(date);
        LocalDateTime startOfDay = today.atStartOfDay(); // 오늘 날짜의 00:00:00
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay(); // 내일 날짜의 00:00:00

        // 같은 userId를 가진 RecordTime 찾기
        int recordTime = recordRepository.findRecordByUserIdToday(userId, startOfDay, endOfDay).orElse(0);

        return RecordDTO.RecordTimeResponse.builder()
                .recordTime(recordTime)
                .build();
    }

    public StudyGoalDTO.GetStudyGoal getStudyGoal(String month) {
        // 단일 자릿수 월을 두 자릿수로 만듭니다.
        String formattedMonth = String.format("%02d", Integer.parseInt(month));

        String currentYear = String.valueOf(LocalDate.now().getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate targetDate = LocalDate.parse(currentYear + "-" + formattedMonth + "-01", formatter).plusMonths(1);
        LocalDateTime targetDateTime = targetDate.atStartOfDay();

        Pageable pageable = PageRequest.of(0, 1);  // 첫 번째 페이지, 한 개의 요소
        List<StudyGoalEntity> studyGoals = studyGoalRepository.findLatestGoalsBeforeDate(targetDateTime, pageable);

        if (studyGoals.isEmpty()) {
            return null; // 또는 적절한 예외 처리
        }

        StudyGoalEntity latestGoal = studyGoals.get(0);

        return StudyGoalDTO.GetStudyGoal.builder()
                .studyGoal(latestGoal.getStudyGoal())
                .build();
    }

    public List<MemberDTO.rankMember> findTimeTop5(String month){
        List<RecordEntity> top5RecordEntities = null; //recordRepository.findTimeTop5();
        List<MemberDTO.rankMember> top5Members = new ArrayList<>();

        if(top5RecordEntities.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }

        for (RecordEntity recordEntity : top5RecordEntities) {
            MemberDTO.rankMember top5Member = MemberDTO.rankMember.builder()
                    .totalRecordTime(recordEntity.getRecordTime())
                    .memberId(recordEntity.getMemberEntity().getMemberId())
                    .memberName(recordEntity.getMemberEntity().getMemberName())
                    .memberMajor(recordEntity.getMemberEntity().getMemberMajor())
                    .build();
            top5Members.add(top5Member);
        }

        return top5Members;
    }

    public List<MemberDTO.rankMember> findDayTop5(String month){
        List<RecordEntity> top5RecordEntities = null; //recordRepository.findDayTop5();
        List<MemberDTO.rankMember> top5Members = new ArrayList<>();

        if(top5RecordEntities.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "저장된 회원 정보가 없습니다.");
        }

        for (RecordEntity recordEntity : top5RecordEntities) {
            MemberDTO.rankMember top5Member = MemberDTO.rankMember.builder()
                    .totalRecordTime(recordEntity.getRecordTime())
                    .memberId(recordEntity.getMemberEntity().getMemberId())
                    .memberName(recordEntity.getMemberEntity().getMemberName())
                    .memberMajor(recordEntity.getMemberEntity().getMemberMajor())
                    .build();
            top5Members.add(top5Member);
        }

        return top5Members;
    }
}
