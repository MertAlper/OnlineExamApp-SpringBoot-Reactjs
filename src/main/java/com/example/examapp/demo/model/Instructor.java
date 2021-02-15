package com.example.examapp.demo.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "INSTRUCTOR")
@Getter
@Setter
@NoArgsConstructor
public class Instructor extends User {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "publisher")
    private List<Exam> publishedExams;

    public void addToPublishedExams(Exam exam){
        publishedExams.add(exam);
        exam.setPublisher(this);
    }

}
