package com.example.examapp.demo.dto;

import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceDto {

    private long id;

    private boolean attended;

    private int numOfTrues;

    private int numOfFalses;

    private int pointReceived;

    private long studentId;

    private long examId;

}

