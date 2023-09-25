package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.RecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {

    @Query("SELECT e.recordTime FROM RecordEntity e WHERE e.userId = :userId AND e.createdTime >= :startOfDay AND e.createdTime < :endOfDay")
    Optional<Integer> findRecordByUserIdToday(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    Optional<RecordEntity> findByUserId(String userId);
}
