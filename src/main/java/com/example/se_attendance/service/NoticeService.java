package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.domain.entity.NoticeEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 가져오기
    public NoticeDTO.GetNotice getNotice() {

        // 최신 공지사항 리스트를 가져옴
        List<NoticeEntity> notices = noticeRepository.findAllByOrderByCreatedTimeDesc();

        // 공지 사항이 없는 경우
        if(notices.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "공지 사항이 없습니다.");
        }

        // 최신 공지사항 추출
        NoticeEntity noticeEntity = notices.get(0);

        return  NoticeDTO.GetNotice.builder()
                .noticeTitle(noticeEntity.getNoticeTitle())
                .noticeContent(noticeEntity.getNoticeContent())
                .createTime(noticeEntity.getCreatedTime())
                .updateTime(noticeEntity.getUpdatedTime())
                .build();
    }
}
