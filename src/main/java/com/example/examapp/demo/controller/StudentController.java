package com.example.examapp.demo.controller;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.dto.StudentDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Student;
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

    @Autowired
    private Dao<Student> studentDao;

    @Autowired
    private Dao<Exam> examDao;

    @GetMapping("{studentId}")
    public StudentDto getStudent(@PathVariable("studentId") long studentId){

        // TODO: Use HATEOAS for inner objects

        Student student = studentDao.getEntityById(studentId);
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
            
//            AttendanceDto attendanceDto = AttendanceDto.builder()
//                    .student(null)
//                    .exam(
//                            ExamDto.builder()
//                                    .examId(att.getExam().getExamId())
//                                    .endDate(att.getExam().getEndDate())
//                                    .questions(att.getExam().getQuestions())
//                                    .startDate(att.getExam().getStartDate())
//                                    .title(att.getExam().getTitle())
//                                    .build()
//                    )
//                    .attended(att.isAttended())
//                    .pointReceived(att.getPointReceived())
//                    .id(att.getId())
//                    .numOfTrues(att.getNumOfTrues())
//                    .numOfFalses(att.getNumOfFalses())
//                    .build();

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

    @PostMapping()
    public ResponseEntity createStudent(@RequestBody StudentDto studentDto){

        Student student = new Student();
        student.setUsername(studentDto.getUserName());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());

        student = studentDao.save(student);

        Map<String, String> map = new HashMap<>();
        map.put("message", "student was created successfully");
        map.put("studentId", String.valueOf(student.getUserId()));

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("{studentId}/{examId}")
    public void registerUserToExam(@PathVariable("studentId") long studentId,
                                   @PathVariable("examId") long examId){

        // TODO: return attendance info
        // TODO: Use HATEOAS for inner objects.
        // TODO: This method should be replaced by dynamic URL

        Exam exam = examDao.getEntityById(examId);
        Student student = studentDao.getEntityById(studentId);

        Attendance attendance = Attendance.builder()
                .exam(exam)
                .student(student)
                .attended(false)
                .build();

        exam.addAttendance(attendance);
        student.addAttendance(attendance);

        studentDao.save(student);
    }

    @PutMapping
    public void saveExamResult(@RequestBody AttendanceDto attendanceDto){

        Student student = studentDao.getEntityById(attendanceDto.getStudentId());

        Attendance attendance = student.getAttendanceList().stream()
                .filter(att -> att.getId() == attendanceDto.getId())
                .findFirst()
                .get();

        attendance.setAttended(attendanceDto.isAttended());
        attendance.setNumOfFalses(attendanceDto.getNumOfFalses());
        attendance.setNumOfTrues(attendanceDto.getNumOfTrues());
        attendance.setPointReceived(attendanceDto.getPointReceived());

        studentDao.save(student);

//        Attendance attendance = Attendance.builder()
//                .student(studentDao.getEntityById(attendanceDto.getStudentId()))
//                .exam(examDao.getEntityById(attendanceDto.getExamId()))
//                .id(attendanceDto.getExamId())
//                .numOfFalses(attendanceDto.getNumOfFalses())
//                .numOfTrues(attendanceDto.getNumOfTrues())
//                .pointReceived(attendanceDto.getPointReceived())
//                .attended(attendanceDto.isAttended())
//                .build();
//

    }
}
