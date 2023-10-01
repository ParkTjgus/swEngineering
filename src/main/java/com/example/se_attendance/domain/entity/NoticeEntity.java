package com.example.se_attendance.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "notice_table")
public class NoticeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noticeTitle;
    private String noticeContent;
}
