package com.example.examapp.demo.controller;

import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final GenericService<Exam> examService;

    @Autowired
    public ExamController(GenericService<Exam> genericService){
        examService = genericService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createExam(@RequestBody ExamDto examDto){

        // TODO: Create a unique URL for the exam.

        Exam exam = Exam.builder()
                .endDate(examDto.getEndDate())
                .title(examDto.getTitle())
                .startDate(examDto.getStartDate())
                .questions(examDto.getQuestions())
                .attendances(new ArrayList<>())
                .build();

        exam = examService.save(exam);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Exam was created");
        map.put("examId", String.valueOf(exam.getExamId()));

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @GetMapping("{examId}")
    public ExamDto getExam(@PathVariable("examId") long examId){

        // TODO: Use HATEOAS for inner objects.

        Exam exam = examService.getById(examId);
        List<AttendanceDto> attendanceDtos = new ArrayList<>();

        for (Attendance att: exam.getAttendances()) {

            AttendanceDto attendanceDto = AttendanceDto.builder()
                    .examId(att.getExam().getExamId())
                    .attended(att.isAttended())
                    .id(att.getId())
                    .numOfFalses(att.getNumOfFalses())
                    .numOfTrues(att.getNumOfTrues())
                    .pointReceived(att.getPointReceived())
                    .studentId(att.getStudent().getUserId())
                    .build();

            attendanceDtos.add(attendanceDto);
        }

        return ExamDto.builder()
                .examId(exam.getExamId())
                .title(exam.getTitle())
                .startDate(exam.getStartDate())
                .endDate(exam.getEndDate())
                .questions(exam.getQuestions())
                .attendanceList(attendanceDtos)
                .build();
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
