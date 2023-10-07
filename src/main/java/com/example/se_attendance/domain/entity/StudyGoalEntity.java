package com.example.se_attendance.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "study_goal_table")
public class StudyGoalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    private int studyGoal;
}
