package com.example.examapp.demo.dto;

import com.example.examapp.demo.model.Question;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDto implements Serializable {

    private String title;

    private Date endDate;

    private Date startDate;

    private long publisherId;

    private List<Question> questions;

    private List<AttendanceDto> attendanceList;

}
