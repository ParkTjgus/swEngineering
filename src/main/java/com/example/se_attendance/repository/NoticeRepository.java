package com.example.se_attendance.repository;

import com.example.se_attendance.domain.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Long> {

    // 최신 생성시간 순으로 공지사항을 정렬하여 가져온다.
    List<NoticeEntity> findAllByOrderByCreateTimeDesc();

    Optional<NoticeEntity> findById(Long id);
}
