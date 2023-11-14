package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    @Query("SELECT e.recordTime FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND e.createTime >= :startOfDay AND e.createTime < :endOfDay")
    Optional<Integer> findRecordByUserIdToday(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT e FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND e.createTime >= :startOfDay AND e.createTime < :endOfDay")
    Optional<RecordEntity> findByUserIdToday(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT e FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND MONTH(e.createTime) = CAST(:month AS integer)")
    List<RecordEntity> findByUserIdMonth(String userId, String month);

    @Query("SELECT DISTINCT r.memberEntity.memberId FROM RecordEntity r WHERE FUNCTION('MONTH', r.updateTime) = :month GROUP BY r.memberEntity.memberId HAVING SUM(r.recordTime) >= :targetTime")
    List<Long> findAllTime(@Param("targetTime") Long targetTime, @Param("month") int month);

    //기록시간순으로 top5 정렬하여 가져온다.
    List<RecordEntity> findTop5ByCreateTimeOrderByRecordTimeDesc(String createTime);
}
