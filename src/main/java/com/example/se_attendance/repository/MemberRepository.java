package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional<MemberEntity> findByMemberId(String memberId);


    void deleteByMemberId(String memberId);
}
