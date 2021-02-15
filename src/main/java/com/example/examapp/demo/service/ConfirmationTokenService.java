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

    /**
     * Gets the ConfirmationToken object that the given token represents.
     * Checks if such a confirmation token exists or whether it has expired.
     * Then, sets the current logged in user as the participant of the exam.
     * @param token
     */
    public void processToken(String token){
        ConfirmationToken confirmationToken = getToken(token);

        if (token == null) {
            throw new IllegalStateException("token doesn't exist");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token has expired");
        }

        // The code guarantees that this username belongs to a student.
        // Because only a student can access this part of the app.
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
