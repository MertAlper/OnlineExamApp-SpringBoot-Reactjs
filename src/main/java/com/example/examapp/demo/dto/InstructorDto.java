package com.example.examapp.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto extends UserDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ExamDto> publishedExams;

}
