package com.example.examapp.demo.dao;

import com.example.examapp.demo.model.Attendance;
import com.example.examapp.demo.model.Exam;
import com.example.examapp.demo.model.Instructor;
import com.example.examapp.demo.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
@Transactional
public class InstructorDaoImpl implements Dao<Instructor> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Instructor getEntityById(long id) {
        Session session = sessionFactory.getCurrentSession();

        try {
            Instructor instructor = session.find(Instructor.class, id);
            instructor.setPublishedExams(getExams(instructor));
            return instructor;
        } catch (NoResultException exp) {
            return null;
        }
    }

    @Override
    public List<Instructor> getAllEntities() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(
                "from Instructor i " +
                        "left join fetch i.publishedExams", Instructor.class
        ).getResultList();

    }

    @Override
    public Instructor save(Instructor obj) {
        Session session = sessionFactory.getCurrentSession();
        return (Instructor) session.merge(obj);
    }

    @Override
    public void delete(Instructor obj) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(obj);
    }

    public Instructor getByUsername(String username){
        Session session = sessionFactory.getCurrentSession();

        try {
            Instructor instructor = session.createQuery(
                             "from Instructor i " +
                        "where i.username = :username", Instructor.class)
                    .setParameter("username", username)
                    .getSingleResult();

            instructor.setPublishedExams(getExams(instructor));
            return instructor;

        } catch (NoResultException exp) {
            return null;
        }
    }

    private List<Exam> getExams(Instructor instructor){
        Session session = sessionFactory.getCurrentSession();

        List<Exam> exams = session.createQuery(
                "from Exam e " +
                        "left join fetch e.attendances " +
                        "where e.publisher = :instructor", Exam.class)
                .setParameter("instructor", instructor)
                .getResultList();

        return exams;
    }

}
