package com.example.examapp.demo.controller.mapper;

import com.example.examapp.demo.dto.ExamDto;
import com.example.examapp.demo.dto.InstructorDto;
import com.example.examapp.demo.model.Instructor;

import java.util.List;

public class InstructorDtoMapper {

    public static InstructorDto getInstructorDto(Instructor instructor){
        InstructorDto instructorDto = new InstructorDto();

        instructorDto.setEmail(instructor.getEmail());
        instructorDto.setFirstName(instructor.getFirstName());
        instructorDto.setLastName(instructor.getLastName());
        instructorDto.setUserName(instructor.getUsername());

        List<ExamDto> examDtoList = ExamDtoMapper.getExamDtos(instructor.getPublishedExams());
        instructorDto.setPublishedExams(examDtoList);

        return instructorDto;
    }

}
