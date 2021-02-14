package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.model.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ExamService implements GenericService<Exam> {

    @Autowired
    private Dao<Exam> examDao;

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

        Exam exam = examDao.save(obj);
        return exam;

    }

    @Override
    @Transactional
    public void delete(Exam obj) {
        examDao.delete(obj);
    }


}
