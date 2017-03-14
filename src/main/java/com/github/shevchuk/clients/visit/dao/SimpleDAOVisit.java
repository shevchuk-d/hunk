package com.github.shevchuk.clients.visit.dao;

import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class SimpleDAOVisit implements DAOVisit{
    private SessionFactory sessionFactory;
    private DAOUtils daoUtils;

    @Override
    public void addVisit(Visit visit) {
        daoUtils.add(sessionFactory, visit);
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

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
