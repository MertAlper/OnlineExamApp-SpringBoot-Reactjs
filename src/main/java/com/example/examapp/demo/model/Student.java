package com.example.examapp.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STUDENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "student")
    private List<Attendance> attendanceList = new ArrayList<>();

    public void addAttendance(Attendance att){
        attendanceList.add(att);
        att.setStudent(this);
    }

}
