package com.example.examapp.demo.controller.mapper;

import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.model.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceDtoMapper {

    public static List<AttendanceDto> getAttendanceDtos(List<Attendance> atts){

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for (Attendance att: atts){
            attendanceDtoList.add(getAttendanceDto(att));
        }
        return attendanceDtoList;
    }


    public static AttendanceDto getAttendanceDto(Attendance att){

        AttendanceDto attendanceDto = AttendanceDto.builder()
                .studentId(att.getStudent().getUserId())
                .examId(att.getExam().getExamId())
                .attended(att.isAttended())
                .pointReceived(att.getPointReceived())
                .id(att.getId())
                .numOfTrues(att.getNumOfTrues())
                .numOfFalses(att.getNumOfFalses())
                .build();

        return attendanceDto;
    }
}
