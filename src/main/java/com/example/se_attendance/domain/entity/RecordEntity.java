package com.example.se_attendance.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "record_table")
public class RecordEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private int recordTime;
}
