package com.example.examapp.demo.service;

import com.example.examapp.demo.config.security.ApplicationUser;
import com.example.examapp.demo.config.security.ApplicationUserRole;
import com.example.examapp.demo.config.security.ApplicationUserService;
import com.example.examapp.demo.dto.RegistrationRequest;
import com.example.examapp.demo.model.Instructor;
import com.example.examapp.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RegistrationService {

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    public void register(RegistrationRequest request) {

        if (!(request.getRole().equalsIgnoreCase("student") || request.getRole().equalsIgnoreCase("instructor"))){
            throw new IllegalStateException("there is no such role");
        }


        applicationUserService.signUpUser(
                new ApplicationUser(
                        request.getUsername(),
                        request.getPassword(),
                        true, // TODO: should be implemented
                        request.getRole().equalsIgnoreCase("student")
                                ? ApplicationUserRole.STUDENT : ApplicationUserRole.INSTRUCTOR
                )
        );

        if (request.getRole().equalsIgnoreCase("student")) {
            Student student = new Student();
            student.setUsername(request.getUsername());
            student.setEmail(request.getEmail());
            student.setAttendanceList(new ArrayList<>());
            student.setFirstName(request.getFirstName());
            student.setLastName(request.getLastName());
            studentService.save(student);
        } else {
            Instructor instructor = new Instructor();
            instructor.setUsername(request.getUsername());
            instructor.setEmail(request.getEmail());
            instructor.setPublishedExams(new ArrayList<>());
            instructor.setFirstName(request.getFirstName());
            instructor.setLastName(request.getLastName());
            instructorService.save(instructor);
        }

    }

}
