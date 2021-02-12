package com.example.examapp.demo.controller;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Student;
import com.example.examapp.demo.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Autowired
    private GenericService<Exam> examService;

    @Autowired
    private Dao<Student> studentDao;

    @PostMapping
    public ResponseEntity createExam(@RequestBody ExamDto examDto){

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

        return new ResponseEntity(map, HttpStatus.OK);

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


        ExamDto examDto = ExamDto.builder()
                .examId(exam.getExamId())
                .title(exam.getTitle())
                .startDate(exam.getStartDate())
                .endDate(exam.getEndDate())
                .questions(exam.getQuestions())
                .attendanceDtoList(attendanceDtos)
                .build();

        return examDto;
    }

//    @GetMapping("{examId}/attendances")
//    public List<AttendanceDto> getAttendances(@PathVariable long examId){
//
//        Exam exam = examDao.getEntityById(examId);
//
//        List<AttendanceDto> attendanceDtos = new ArrayList<>();
//
//        for (Attendance att: exam.getAttendances()){
//
//            AttendanceDto attendanceDto = AttendanceDto.builder()
//                    .examId(att.getExam().getExamId())
//                    .attended(att.isAttended())
//                    .id(att.getId())
//                    .numOfFalses(att.getNumOfFalses())
//                    .numOfTrues(att.getNumOfTrues())
//                    .pointReceived(att.getPointReceived())
//                    .studentId(att.getStudent().getUserId())
//                    .build();
//
//            // TODO: Discuss two ways
//
////            AttendanceDto attendanceDto = AttendanceDto.builder()
////                    .exam(null)
////                    .attended(att.isAttended())
////                    .id(att.getId())
////                    .numOfFalses(att.getNumOfFalses())
////                    .numOfTrues(att.getNumOfTrues())
////                    .pointReceived(att.getPointReceived())
////                    .student(
////                            StudentDto.builder()
////                            .email(att.getStudent().getEmail())
////                            .firstName(att.getStudent().getFirstName())
////                            .lastName(att.getStudent().getLastName())
////                            .userId(att.getStudent().getUserId())
////                            .build()
////                    )
////                    .build();
//
//            attendanceDtos.add(attendanceDto);
//        }
//
//        return attendanceDtos;
//    }

}
