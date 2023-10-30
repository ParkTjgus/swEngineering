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

    public void createNotice(NoticeDTO.NoticeDto noticeDto) {
        NoticeEntity notice = new NoticeEntity(noticeDto.getNoticeContent());
        // 왜 이렇게 생성하지? builder로 생성하면 안되나? id는 auto로 자동 생성되는데 왜 dto에서 아이디를 가져오는거지? 이해가 안된다..
        log.info(notice.getId() + notice.getNoticeContent());
        noticeRepository.save(notice);
    }

    public NoticeDTO.NoticeDto findNotice(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
        return NoticeDTO.NoticeDto.builder()
                .id(notice.getId())
                .noticeContent(notice.getNoticeContent())
                .build();
    }

    @Transactional(readOnly = true)
    public List<NoticeDTO.NoticeDto> findAll() {
        return noticeRepository.findAll().stream()
                .map(notice -> NoticeDTO.NoticeDto.builder()
                        .id(notice.getId())
                        .noticeContent(notice.getNoticeContent())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateNotice(Long id, NoticeDTO.NoticeDto noticeDto) { //Long으로 리턴해서 바뀐 게시글 id값을 받아올까? 고민
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
        //db에 안날려도 알아서 업데이트 해준대.. 신기(더티체킹)
        notice.update(noticeDto.getNoticeContent());

        return id;
    }

    public void deleteNotice(Long id) {
        NoticeEntity notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));
        noticeRepository.delete(notice);
    }
}
