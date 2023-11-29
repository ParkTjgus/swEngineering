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

    @Query("SELECT e FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND DATE(e.createTime) = :date")
    List<RecordEntity> findByUserIdDate(String userId, String date);

    //기록시간순으로 top5 정렬하여 가져온다.
    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, SUM(r.recordTime)" +
            "FROM RecordEntity r " +
            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberId " +
            "ORDER BY SUM(r.recordTime) DESC LIMIT 5")
    List<RecordEntity> findTimeTop5();

    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, count(r.createTime) " +
            "FROM RecordEntity r " +
            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberId " +
            "ORDER BY count(r.createTime) DESC LIMIT 5")
    List<RecordEntity> findDayTop5();
}
