package com.example.examapp.demo.controller;

import com.example.examapp.demo.controller.mapper.StudentDtoMapper;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.StudentDto;
import com.example.examapp.demo.model.Student;
import com.example.examapp.demo.service.ConfirmationTokenService;
import com.example.examapp.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/students")
@RestController
public class StudentController {

    private final StudentService studentService;
    private final ConfirmationTokenService tokenService;
    @Autowired
    public StudentController(StudentService studentService, ConfirmationTokenService tokenService){
        this.studentService = studentService;
        this.tokenService = tokenService;
    }

    @GetMapping(path = "{user}")
    public StudentDto getStudent(@PathVariable("user") String username){

        // TODO: Use HATEOAS for inner objects

        Student student = studentService.getByUsername(username);

        // Maps Student to StudentDto
        StudentDto studentDto = StudentDtoMapper.getStudentDto(student);

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
