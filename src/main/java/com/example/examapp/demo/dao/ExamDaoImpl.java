package com.example.examapp.demo.dao;

import com.example.examapp.demo.model.Exam;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class ExamDaoImpl implements Dao<Exam> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Exam getEntityById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Exam exam = session.createQuery(
                "select DISTINCT e " +
                    "from Exam e " +
                    "left join fetch e.attendances " +
                    "where e.id = :id", Exam.class)
                .setParameter("id", id)
                .getSingleResult();

        return exam;
    }

    @Override
    public List<Exam> getAllEntities() {
        Session session = sessionFactory.getCurrentSession();
        List<Exam> exams = session.createQuery(
                "from Exam e left join fetch e.attendances",
                Exam.class).getResultList();

        return exams;
    }

    @Override
    public Exam save(Exam obj) {
        Session session = sessionFactory.getCurrentSession();
        return (Exam)session.merge(obj);
    }

    @Override
    public void delete(Exam obj) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(obj);
    }
}
