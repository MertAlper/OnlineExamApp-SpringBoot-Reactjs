package com.example.examapp.demo.controller.mapper;

import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamDtoMapper {

    public static List<ExamDto> getExamDtos(List<Exam> exams) {
        List<ExamDto> examDtos = new ArrayList<>();

        for (Exam exam: exams){
            examDtos.add(getExamDto(exam));
        }

        return examDtos;
    }

    public static ExamDto getExamDto(Exam exam){

        List<AttendanceDto> attendanceDtos = attendanceDtos = AttendanceDtoMapper.getAttendanceDtos(exam.getAttendances());

        return ExamDto.builder()
                .examId(exam.getExamId())
                .title(exam.getTitle())
                .startDate(exam.getStartDate())
                .endDate(exam.getEndDate())
                .questions(exam.getQuestions())
                .attendanceList(attendanceDtos)
                .publisherId(exam.getPublisher().getUserId())
                .build();
    }



}
