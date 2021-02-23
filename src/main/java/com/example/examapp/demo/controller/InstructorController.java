package com.example.examapp.demo.controller;

import com.example.examapp.demo.controller.mapper.InstructorDtoMapper;
import com.example.examapp.demo.dto.InstructorDto;
import com.example.examapp.demo.model.Instructor;
import com.example.examapp.demo.service.InstructorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping(path = "{user}")
    public InstructorDto getInstructor(@PathVariable("user") String username){

        Instructor instructor = instructorService.getByUsername(username);
        InstructorDto instructorDto = InstructorDtoMapper.getInstructorDto(instructor);

        return instructorDto;
    }

}
