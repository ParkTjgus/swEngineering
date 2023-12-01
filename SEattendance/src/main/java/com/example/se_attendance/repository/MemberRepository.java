package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByMemberId(String memberId);

    @Query("DELETE from MemberEntity m where m.memberId = :memberId")
    @Modifying
    int deleteByMemberId(@Param("memberId") String memberId);

    @Query("SELECT r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberMajor, SUM(r.recordTime) " +
            "FROM RecordEntity r " +
            "WHERE FUNCTION('MONTH', r.createTime) = FUNCTION('MONTH', CURRENT_DATE) " +
            "GROUP BY r.memberEntity.memberId, r.memberEntity.memberName, r.memberEntity.memberId ")
    List<Object[]> findMembers();
}
