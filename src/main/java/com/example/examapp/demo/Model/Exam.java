package com.example.examapp.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "EXAM")
@Entity
@Getter
@Setter
@AllArgsConstructor
public class Exam {
}
