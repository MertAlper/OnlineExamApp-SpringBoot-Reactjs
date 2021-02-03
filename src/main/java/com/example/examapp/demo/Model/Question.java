package com.example.examapp.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Table(name = "QUESTION")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Question implements Serializable {

    @Id
    @Column(name = "QUESTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "QUESTION_TITLE")
    private String questionTitle;

    @ManyToOne
    @JoinColumn(name ="EXAM_ID")
    private  Exam exam;

    @OneToMany(mappedBy="question")
    private Set<Choice> choices;










}
