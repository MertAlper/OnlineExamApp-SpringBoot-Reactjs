package com.example.examapp.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "CHOICE")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Choice implements Serializable {

    @Id
    @Column(name = "ChoiceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long choiceId;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "isTrue", nullable = false)
    private boolean correct;

}
