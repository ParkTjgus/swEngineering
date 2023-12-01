package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.MemberEntity;
import com.example.se_attendance.domain.entity.RecordEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<RecordEntity, Long> {


    @Query("SELECT e.recordTime FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND e.createTime >= :startOfDay AND e.createTime < :endOfDay")
    Optional<Integer> findRecordByUserIdToday(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT e FROM RecordEntity e WHERE e.memberEntity.memberId = :userId AND e.createTime >= :startOfDay AND e.createTime < :endOfDay")
    Optional<RecordEntity> findByUserIdToday(String userId, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<RecordEntity> findByMemberEntity(MemberEntity member);

    //한 달 기준 기록시간 순으로 top5 정렬하여 가져온다.
//    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, SUM(r.recordTime) AS totalRecordTime " +
//            "FROM RecordEntity r " +
//            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
//            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberId " +
//            "ORDER BY totalRecordTime DESC LIMIT 5")
//    List<RecordEntity> findTimeTop5();

    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, SUM(r.recordTime) AS totalRecordTime " +
            "FROM RecordEntity r " +
            "WHERE MONTH(r.createTime) = MONTH(CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor " +
            "ORDER BY totalRecordTime DESC")
    List<Object[]> findTop5ByRecordTime(Pageable pageable);

    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, count(r.createTime) AS totalRecordDay " +
            "FROM RecordEntity r " +
            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor " +
            "ORDER BY totalRecordDay DESC")
    List<Object[]> findTop5ByRecordDay(Pageable pageable);
}
