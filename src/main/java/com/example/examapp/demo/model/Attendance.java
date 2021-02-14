package com.example.examapp.demo.model;

import lombok.*;
import javax.persistence.*;

@Table(name = "ATTENDANCE")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {

    @Id
    @Column(name = "AttendanceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "isAttended")
    private boolean attended;

    @Column(name = "NumOfTrues")
    private int numOfTrues;

    @Column(name = "NumOfFalses")
    private int numOfFalses;

    @Column(name = "PointReceived")
    private int pointReceived;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "ExamId")
    private Exam exam;

}
