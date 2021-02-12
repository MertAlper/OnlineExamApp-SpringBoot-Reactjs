package com.example.examapp.demo.model;

import com.example.examapp.demo.util.ExamSerializer;
import com.example.examapp.demo.util.StudentSerializer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
