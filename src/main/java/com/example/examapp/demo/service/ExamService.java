package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ExamService implements GenericService<Exam> {

    @Autowired
    private Dao<Exam> examDao;

    @Autowired
    private GenericService<Instructor> instructorService;

    @Override
    @Transactional
    public Exam getById(long id) {

        Exam exam = examDao.getEntityById(id);

        if (exam == null){
            throw new EntityNotFoundException();
        }

        return exam;
    }

    @Override
    @Transactional
    public List<Exam> getAll() {

        List<Exam> exams = examDao.getAllEntities();
        return exams;

    }

    @Override
    @Transactional
    public Exam save(Exam obj) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Instructor instructor = ((InstructorService)instructorService).getByUsername(username);
        instructor.addToPublishedExams(obj);

        Exam exam = examDao.save(obj);
        return exam;
    }

    @Override
    @Transactional
    public void delete(Exam obj) {
        examDao.delete(obj);
    }


}
