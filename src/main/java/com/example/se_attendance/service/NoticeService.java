package com.example.se_attendance.service;

import com.example.se_attendance.domain.dto.NoticeDTO;
import com.example.se_attendance.domain.entity.NoticeEntity;
import com.example.se_attendance.exeption.AppException;
import com.example.se_attendance.exeption.ErrorCode;
import com.example.se_attendance.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 공지사항 가져오기
    public NoticeDTO.GetNotice getNotice() {

        // 최신 공지사항 리스트를 가져옴
        List<NoticeEntity> notices = noticeRepository.findAllByOrderByCreateTimeDesc();

        // 공지 사항이 없는 경우
        if(notices.isEmpty()) {
            throw new AppException(ErrorCode.NOT_FOUND, "공지 사항이 없습니다.");
        }

        // 최신 공지사항 추출
        NoticeEntity noticeEntity = notices.get(0);

        return  NoticeDTO.GetNotice.builder()
                .noticeContent(noticeEntity.getNoticeContent())
                .createTime(noticeEntity.getCreateTime())
                .updateTime(noticeEntity.getUpdateTime())
                .build();
    }

       // 공지사항 생성
    public void createNotice(NoticeDTO.NoticeDto noticeDto) {
        NoticeEntity notice = new NoticeEntity(noticeDto.getNoticeContent());
        noticeRepository.save(notice);
    }

    // 공지사항 상세 조회
    public NoticeDTO.NoticeDto findNotice(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 공지사항이 존재하지 않습니다."));
        return NoticeDTO.NoticeDto.builder()
                .id(notice.getId())
                .noticeContent(notice.getNoticeContent())
                .build();
    }

    // 공지사항 목록 조회
    @Transactional(readOnly = true)
    public List<NoticeDTO.NoticeDto> findAll() {
        return noticeRepository.findAll().stream()
                .map(notice -> NoticeDTO.NoticeDto.builder()
                        .id(notice.getId())
                        .noticeContent(notice.getNoticeContent())
                        .build())
                .collect(Collectors.toList());
    }

    // 공지사항 삭제
    public void deleteNotice(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "해당 공지사항이 존재하지 않습니다."));
        noticeRepository.delete(notice);
    }
}
