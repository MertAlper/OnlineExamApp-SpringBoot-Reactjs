package com.example.examapp.demo.model;

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

    /**
     * Adds the given exam to the instructor's attendanceList.
     * And also sets the publisher of the given exam object to this instructor.
     * So there is no need to set it again in another place.
     * @param exam
     */
    public void addToPublishedExams(Exam exam){
        publishedExams.add(exam);
        exam.setPublisher(this);
    }

}
