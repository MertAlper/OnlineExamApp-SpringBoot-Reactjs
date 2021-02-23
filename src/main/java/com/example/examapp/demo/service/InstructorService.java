package com.example.examapp.demo.service;

import com.example.examapp.demo.dao.Dao;
import com.example.examapp.demo.dao.InstructorDaoImpl;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

@Service
public class InstructorService implements GenericService<Instructor> {

    @Autowired
    private Dao<Instructor> instructorDao;

    @Override
    public Instructor getById(long id) {
        return instructorDao.getEntityById(id);
    }

    @Override
    public List<Instructor> getAll() {
        return instructorDao.getAllEntities();
    }

    @Override
    public Instructor save(Instructor obj) {
        return instructorDao.save(obj);
    }

    @Override
    public void delete(Instructor obj) {
        instructorDao.delete(obj);
    }

    public Instructor getByUsername(String username){
        return ((InstructorDaoImpl)instructorDao).getByUsername(username);
    }

    public List<Exam> getPublishedExams(Instructor instructor){
        return ((InstructorDaoImpl)instructorDao).getExams(instructor);
    }

}
