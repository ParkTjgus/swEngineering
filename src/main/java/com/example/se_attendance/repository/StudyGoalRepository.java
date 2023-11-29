package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.StudyGoalEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyGoalRepository extends JpaRepository<StudyGoalEntity, Long> {
    // 가장 최근에 설정한 목표시간 가져오기
    @Query("SELECT e FROM StudyGoalEntity e WHERE e.createTime <= :targetDate ORDER BY e.createTime DESC")
    List<StudyGoalEntity> findLatestGoalsBeforeDate(LocalDateTime targetDate, Pageable pageable);

    // 목표시간 달성한 멤버 출력하기
    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, SUM(r.recordTime) " +
            "FROM RecordEntity r " +
            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberId " +
            //r.memberEntity.memberId -> r.memberEntity.memberMajor 로 수정??
            "HAVING SUM(r.recordTime) > :targetTime")
    List<Object[]> findMembers(@Param("targetTime") Long targetTime);
}
