package com.example.examapp.demo.model;

import lombok.*;
import javax.persistence.*;

/**
 * This class represents the attendance relationship between a student and an exam.
 * When a user visits an exam's link,
 * an object of the class with default values will be created to save who have registered to which exam.
 * After the student attends to the exam,
 * the values of the object will be updated based on the exam result.
 */

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
