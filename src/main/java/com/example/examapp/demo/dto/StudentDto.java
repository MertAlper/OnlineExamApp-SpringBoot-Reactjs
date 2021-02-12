package com.example.examapp.demo.dto;

import com.example.examapp.demo.model.Attendance;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentDto extends UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AttendanceDto> attendanceList = new ArrayList<>();

}
