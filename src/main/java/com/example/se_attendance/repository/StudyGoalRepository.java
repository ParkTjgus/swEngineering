package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.StudyGoalEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudyGoalRepository extends JpaRepository<StudyGoalEntity, Long> {
    @Query("SELECT e FROM StudyGoalEntity e WHERE e.createTime <= :targetDate ORDER BY e.createTime DESC")
    List<StudyGoalEntity> findLatestGoalsBeforeDate(LocalDateTime targetDate, Pageable pageable);
}
