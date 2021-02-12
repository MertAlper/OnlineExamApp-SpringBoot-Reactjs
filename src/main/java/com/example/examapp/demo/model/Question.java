package com.example.examapp.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Table(name = "QUESTION")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    @Id
    @Column(name = "QuestId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionId;

    @Column(name = "QuestTitle", nullable = false)
    private String questionTitle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "QuestId")
    private List<Choice> choices;

}
