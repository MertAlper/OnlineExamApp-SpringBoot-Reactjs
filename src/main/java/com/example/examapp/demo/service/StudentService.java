package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentService implements GenericService<Student> {

    @Autowired
    private Dao<Student> studentDao;

    @Autowired
    private Dao<Exam> examDao;

    @Override
    public Student getById(long id) {

        Student student = studentDao.getEntityById(id);

        if (student == null)
            throw new EntityNotFoundException();

        return student;
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAllEntities();
    }

    @Override
    public Student save(Student obj) {
        return studentDao.save(obj);
    }

    @Override
    public void delete(Student obj) {
        studentDao.delete(obj);
    }

    public void saveExamResult(AttendanceDto attendanceDto) {

        Student student = studentDao.getEntityById(attendanceDto.getStudentId());

        Attendance attendance = student.getAttendanceList().stream()
                .filter(att -> att.getId() == attendanceDto.getId())
                .findFirst()
                .get();

        attendance.setAttended(attendanceDto.isAttended());
        attendance.setNumOfFalses(attendanceDto.getNumOfFalses());
        attendance.setNumOfTrues(attendanceDto.getNumOfTrues());
        attendance.setPointReceived(attendanceDto.getPointReceived());

        studentDao.save(student);

    }

    public Attendance registerUserToExam(long studentId, long examId) {

        Exam exam = examDao.getEntityById(examId);
        Student student = getById(studentId);

        Attendance attendance = Attendance.builder()
                .exam(exam)
                .student(student)
                .attended(false)
                .build();

        exam.addAttendance(attendance);
        student.addAttendance(attendance);

        save(student);

        return attendance;
    }
}
