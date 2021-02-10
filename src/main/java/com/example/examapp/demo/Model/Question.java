package com.example.examapp.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Table(name = "QUESTION")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {

    @Id
    @Column(name = "QuestId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "QuestTitle", nullable = false)
    private String questionTitle;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "QuestId")
    private List<Choice> choices;

}
