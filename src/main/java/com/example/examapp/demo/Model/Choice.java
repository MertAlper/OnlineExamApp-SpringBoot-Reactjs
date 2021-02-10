package com.example.examapp.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "CHOICE")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Choice implements Serializable {

    @Id
    @Column(name = "ChoiceId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long choiceId;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "is_True", nullable = false)
    private boolean isTrue;

}
