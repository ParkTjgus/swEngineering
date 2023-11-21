package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.StudyGoalDTO;
import com.example.se_attendance.domain.entity.StudyGoalEntity;
import com.example.se_attendance.repository.StudyGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyGoalService {

    private final StudyGoalRepository studyGoalRepository;
    private final RecordService recordService;


    //목표 시간 설정
    public void setStudyGoal(StudyGoalDTO.SetStudyGoal studyGoalDto) {
        StudyGoalEntity studyGoal = StudyGoalEntity.builder()
                .studyGoal(studyGoalDto.getStudyGoal())
                .build();
        studyGoalRepository.save(studyGoal);
    }

    // 목표시간 달성한 멤버 출력하기 (web)
    public List<MemberDTO.rankMember> findMembers() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        String month = now.format(formatter);

        int targetTimei = recordService.getStudyGoal(month).getStudyGoal();
        Long targetTime = Long.valueOf(targetTimei);

        List<Object[]> result = studyGoalRepository.findMembers(targetTime);


        List<MemberDTO.rankMember> mappedResult = new ArrayList<>();
        for (Object[] row : result) {

            String memberId = (String) row[0];
            String memberName = (String) row[1];
            String memberMajor = (String) row[2];
            int recordTime = ((Number) row[3]).intValue();

            MemberDTO.rankMember member = new MemberDTO.rankMember(memberId, memberName, memberMajor, recordTime);
            mappedResult.add(member);
        }

        return mappedResult;

    }
}
