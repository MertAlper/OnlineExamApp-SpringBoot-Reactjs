package com.example.examapp.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Table(name = "EXAM")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

    @Id
    @Column(name = "EXAM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @OneToMany(mappedBy="exam")
    private Set<Question> questions;
















}
