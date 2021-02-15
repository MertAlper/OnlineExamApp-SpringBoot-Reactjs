package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.ConfirmationTokenRepository;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.ConfirmationToken;
import com.example.examapp.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public long save(ConfirmationToken token){
        return confirmationTokenRepository.save(token);
    }

    public void delete(ConfirmationToken token) {
        confirmationTokenRepository.delete(token);
    }

    public void processToken(String token){
        ConfirmationToken confirmationToken = getToken(token);

        if (token == null) {
            throw new IllegalStateException("token doesn't exist");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token has expired");
        }

        String userName = SecurityContextHolder
                .getContext().getAuthentication().getName();
        Student student = studentService.getByUsername(userName);

        Attendance attendance = Attendance.builder()
                .attended(false)
                .exam(confirmationToken.getExam())
                .student(student)
                .build();

        confirmationToken.getExam().addAttendance(attendance);
        student.addAttendance(attendance);
        studentService.save(student);
    }

}
