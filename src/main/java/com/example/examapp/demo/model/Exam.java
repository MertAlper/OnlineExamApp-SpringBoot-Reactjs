package com.example.examapp.demo.model;


import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Table(name = "EXAM")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Exam implements Serializable {

    @Id
    @Column(name = "ExamId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "StartDate", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ExamId")
    private List<Question> questions;
//
//    @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL)
//    @JoinColumn(name = "ExamId")
//    private ConfirmationToken confirmationToken;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "exam")
    private List<Attendance> attendances;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST
    })
    @JoinColumn(name = "PublisherId", nullable = false)
    private Instructor publisher;

    /**
     * Adds the given attendance to the student's attendanceList.
     * And also sets the student of the given attendance object to this student.
     * So there is no need to set it again in another place.
     * @param att the attendance object
     */

    public void addAttendance(Attendance att){
        attendances.add(att);
        att.setExam(this);
    }

}
