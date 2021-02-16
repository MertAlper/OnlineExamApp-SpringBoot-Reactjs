package com.example.examapp.demo.controller;

import com.example.examapp.demo.controller.mapper.ExamDtoMapper;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.ConfirmationToken;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.service.ConfirmationTokenService;
import com.example.examapp.demo.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final GenericService<Exam> examService;
    private final ConfirmationTokenService tokenService;

    @Autowired
    public ExamController(GenericService<Exam> genericService, ConfirmationTokenService tokenService){
        examService = genericService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createExam(@RequestBody ExamDto examDto){

        // Creating an exam entity based on examDto
        Exam exam = Exam.builder()
                .endDate(examDto.getEndDate())
                .title(examDto.getTitle())
                .startDate(examDto.getStartDate())
                .questions(examDto.getQuestions())
                .attendances(new ArrayList<>())
                .build();

        // Create a unique url for the new exam.
        String token = UUID.randomUUID().toString();
        String link = "http://localhost:8080/examApp/api/students/confirm?token=" + token;

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now().plusDays(21), LocalDateTime.now(), null, exam
        );
        examService.save(exam);

        Map<String, String> properties = new HashMap<>();
        properties.put("examUrl", link);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("{examId}")
    public ExamDto getExam(@PathVariable("examId") long examId){

        // TODO: Use HATEOAS for inner objects.

        Exam exam = examService.getById(examId);
        ExamDto examDto = ExamDtoMapper.getExamDto(exam);

        return examDto;
    }

    @PutMapping("{examId}")
    public void updateExam(@PathVariable("examId") long examId, @RequestBody ExamDto examDto){

        Exam exam = examService.getById(examId);

        exam.setEndDate(examDto.getEndDate());
        exam.setQuestions(examDto.getQuestions());
        exam.setStartDate(examDto.getStartDate());
        exam.setTitle(examDto.getTitle());

        examService.save(exam);
    }

    @DeleteMapping("{examId}")
    public void deleteExam(@PathVariable("examId") long examId){
        Exam exam = examService.getById(examId);

        if (exam == null){
            throw new EntityNotFoundException();
        }

        examService.delete(exam);
    }

}
