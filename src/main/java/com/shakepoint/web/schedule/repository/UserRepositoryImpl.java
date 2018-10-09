package com.shakepoint.web.schedule.repository;

import com.shakepoint.web.schedule.data.entity.UserProfile;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<UserProfile> usersWithBirthDates() {
        return em.createQuery("SELECT p FROM Profile p").getResultList();
    }
}
