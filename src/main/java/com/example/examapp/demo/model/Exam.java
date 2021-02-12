package com.example.examapp.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Table(name = "EXAM")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

    @Id
    @Column(name = "ExamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "StartDate", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ExamId")
    private List<Question> questions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exam")
    private List<Attendance> attendances;

    public void addAttendance(Attendance att){
        attendances.add(att);
        att.setExam(this);
    }

}
