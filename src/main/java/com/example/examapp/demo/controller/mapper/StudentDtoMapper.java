package com.example.examapp.demo.controller.mapper;

import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.StudentDto;
import com.example.examapp.demo.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDtoMapper {

    public static List<StudentDto> getStudentDtos(List<Student> studentList){

        List<StudentDto> studentDtoList = new ArrayList<>();

        for (Student student: studentList){
            studentDtoList.add(getStudentDto(student));
        }

        return studentDtoList;
    }

    public static StudentDto getStudentDto(Student student){

        List<StudentDto> studentDtoList = new ArrayList<>();

        StudentDto studentDto = new StudentDto();

        studentDto.setEmail(student.getEmail());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setUserId(student.getUserId());
        studentDto.setUserName(student.getUsername());

        List<AttendanceDto> attendanceDtoList = AttendanceDtoMapper.getAttendanceDtos(student.getAttendanceList());
        studentDto.setAttendanceList(attendanceDtoList);

        return studentDto;
    }

}
