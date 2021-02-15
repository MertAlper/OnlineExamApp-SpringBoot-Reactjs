package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.dao.StudentDaoImpl;
import com.example.examapp.demo.dto.AttendanceDto;
import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class StudentService implements GenericService<Student> {

    @Autowired
    private Dao<Student> studentDao;

    @Override
    public Student getById(long id) {
        return studentDao.getEntityById(id);
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

    public Student getByUsername(String username) {
        return ((StudentDaoImpl)studentDao).getByUsername(username);
    }
}
