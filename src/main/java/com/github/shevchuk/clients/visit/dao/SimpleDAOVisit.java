package com.github.shevchuk.clients.visit.dao;

import com.github.shevchuk.clients.visit.model.Visit;
import org.hibernate.SessionFactory;


public class SimpleDAOVisit implements DAOVisit{
    private SessionFactory sessionFactory;


    @Override
    public void addVisit(Visit visit) {
        sessionFactory.getCurrentSession().save(visit);
    }

    @Override
    public void removeVisit(Visit visit) {
        sessionFactory.getCurrentSession().delete(visit);
    }

    @Override
    public void updateVisit(Visit visit) {
        sessionFactory.getCurrentSession().update(visit);
    }

    @Override
    public Visit getVisitById(long visitId) {
        return (Visit) sessionFactory.getCurrentSession().load(Visit.class, visitId);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
