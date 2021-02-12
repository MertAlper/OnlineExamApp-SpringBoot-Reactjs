package com.example.examapp.demo.dto;

import lombok.*;

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

