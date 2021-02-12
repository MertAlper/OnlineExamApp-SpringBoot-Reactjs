package com.example.examapp.demo.dto;

import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDto implements Serializable {

    private long examId;

    private String title;

    private Date endDate;

    private Date startDate;

    private List<Question> questions;

    private List<AttendanceDto> attendanceDtoList;

}
