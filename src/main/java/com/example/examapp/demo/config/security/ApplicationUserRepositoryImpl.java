package com.example.examapp.demo.config.security;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Optional;

@Repository
@Transactional
public class ApplicationUserRepositoryImpl implements ApplicationUserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<ApplicationUser> findByUsername(String username){
        Session session = sessionFactory.getCurrentSession();

        try {
            ApplicationUser appUser = session
                    .createQuery("from ApplicationUser where username = :username", ApplicationUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.of(appUser);
        } catch (NoResultException exp){
            return Optional.empty();
        }
    }

}
