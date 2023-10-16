package com.example.se_attendance.domain.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "member_table")
public class MemberEntity {
    @Id
    private String memberId;
    private String memberName;
    private String memberPw;
    private String memberMajor;
    private String memberState;
    private String memberBirth;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createTime;

    @Builder.Default
    @OneToMany(mappedBy = "memberEntity")
    private List<RecordEntity> recordEntityList = new ArrayList<>();

}
