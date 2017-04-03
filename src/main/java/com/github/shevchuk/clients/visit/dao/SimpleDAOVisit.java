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

    public void deleteVisit(long visitId) {
        daoUtils.delete(sessionFactory, visitId, Visit.class);
    }

    @Override
    public void updateVisit(long visitId, Visit updater) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Visit clientPersistent = session.load(Visit.class, visitId);
        clientPersistent.setClient(updater.getClient());
        clientPersistent.setFinish(updater.getFinish());
        clientPersistent.setStart(updater.getStart());
        session.update(clientPersistent);
        transaction.commit();
        session.close();
    }

    @Override
    public Visit getVisitById(long visitId) {
        return (Visit) daoUtils.getById(sessionFactory, visitId, Visit.class);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
