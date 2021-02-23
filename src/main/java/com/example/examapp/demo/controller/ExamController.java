package com.example.examapp.demo.controller;

import com.example.examapp.demo.controller.mapper.ExamDtoMapper;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.ConfirmationToken;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Instructor;
import com.example.examapp.demo.service.ConfirmationTokenService;
import com.example.examapp.demo.service.GenericService;
import com.example.examapp.demo.service.InstructorService;
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
    private  InstructorService instructorService;

    @Autowired
    public ExamController(GenericService<Exam> genericService, ConfirmationTokenService tokenService,
                          InstructorService instructorService){
        examService = genericService;
        this.tokenService = tokenService;
        this.instructorService=instructorService;
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
        String link = "http://localhost:3000/ExamLogin/" + token;

        exam = examService.save(exam);

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token, LocalDateTime.now().plusDays(21), LocalDateTime.now(), null, exam
        );
        tokenService.save(confirmationToken);

        Map<String, String> properties = new HashMap<>();
        properties.put("examUrl", link);
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @GetMapping("/{examId}")
    public ExamDto getExam(@PathVariable("examId") long examId){

        // TODO: Use HATEOAS for inner objects.

        Exam exam = examService.getById(examId);
        ExamDto examDto = ExamDtoMapper.getExamDto(exam);

        return examDto;
    }

    @GetMapping("/token:{token}")
    public ExamDto getExamFromToken(@PathVariable("token") String token){
        // TODO: Use HATEOAS for inner objects.
        ConfirmationToken examToken= tokenService.getToken(token);
        ExamDto examDto=ExamDtoMapper.getExamDto(examToken.getExam());

        return examDto;
    }

    @GetMapping(path="/by/{username}")
    public List<ExamDto> getExamsByPublisher(@PathVariable String username){
        // TODO: Use HATEOAS for inner objects.
        Instructor instructor = instructorService.getByUsername(username);
        List<Exam> exams = instructorService.getPublishedExams(instructor);
        List<ExamDto> examDtos = ExamDtoMapper.getExamDtos(exams);

        return examDtos;

    }

    @PutMapping("/{examId}")
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
