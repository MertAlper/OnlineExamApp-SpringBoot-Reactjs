package com.example.examapp.demo.dao;

import com.example.examapp.demo.model.ConfirmationToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Repository
@Transactional
public class ConfirmationTokenRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public ConfirmationToken findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();

        try {
            return session.createQuery(
                    "from ConfirmationToken t " +
                            "left join fetch t.exam e " +
                            "left join fetch e.attendances " +
                            "where t.token = :token", ConfirmationToken.class)
                    .setParameter("token", token)
                    .getSingleResult();
        } catch (NoResultException exp){
            return null;
        }
    }

    public long save(ConfirmationToken obj) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(obj);
        return 0;
    }

    public void delete(ConfirmationToken obj) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(obj);
    }

}
