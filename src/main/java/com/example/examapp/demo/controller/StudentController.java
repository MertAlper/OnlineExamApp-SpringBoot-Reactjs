package com.example.examapp.demo.controller;

import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.StudentDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Student;
import com.example.examapp.demo.service.ConfirmationTokenService;
import com.example.examapp.demo.service.GenericService;
import com.example.examapp.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/students")
@RestController
public class StudentController {

    private final GenericService<Student> studentService;
    private final ConfirmationTokenService tokenService;
    @Autowired
    public StudentController(GenericService<Student> genericService, ConfirmationTokenService tokenService){
        studentService = genericService;
        this.tokenService = tokenService;
    }

    @GetMapping("{studentId}")
    public StudentDto getStudent(@PathVariable("studentId") long studentId){

        // TODO: Use HATEOAS for inner objects

        Student student = studentService.getById(studentId);

        // Maps Student to StudentDto
        StudentDto studentDto = new StudentDto();

        List<AttendanceDto> attendanceDtoList = new ArrayList<>();
        for (Attendance att: student.getAttendanceList()){

            AttendanceDto attendanceDto = AttendanceDto.builder()
                    .studentId(att.getStudent().getUserId())
                    .examId(att.getExam().getExamId())
                    .attended(att.isAttended())
                    .pointReceived(att.getPointReceived())
                    .id(att.getId())
                    .numOfTrues(att.getNumOfTrues())
                    .numOfFalses(att.getNumOfFalses())
                    .build();
            
            attendanceDtoList.add(attendanceDto);
        }

        studentDto.setEmail(student.getEmail());
        studentDto.setFirstName(student.getFirstName());
        studentDto.setLastName(student.getLastName());
        studentDto.setUserId(student.getUserId());
        studentDto.setUserName(student.getUsername());
        studentDto.setAttendanceList(attendanceDtoList);

        return studentDto;

    }

    @GetMapping(path = "confirm")
    public void registerUserToExam(@RequestParam("token") String token){
        // TODO: Use HATEOAS for inner objects.
        tokenService.processToken(token);
    }

    @PostMapping("saveResult")
    public void saveExamResult(@RequestBody AttendanceDto attendanceDto){

        ((StudentService)studentService).saveExamResult(attendanceDto);

    }
}
