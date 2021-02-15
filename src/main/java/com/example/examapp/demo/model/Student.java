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

    /**
     * Adds the given attendance to the student's attendanceList.
     * And also sets the student of the given attendance object to this student.
     * So there is no need to set it again in another place.
     * @param att, the attendance object.
     */
    public void addAttendance(Attendance att){
        attendanceList.add(att);
        att.setStudent(this);
    }

}
