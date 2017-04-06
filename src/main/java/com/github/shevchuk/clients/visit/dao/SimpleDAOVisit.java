package com.github.shevchuk.clients.visit.dao;

import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.utils.DAOUtils;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;

@Component
public class SimpleDAOVisit implements DAOVisit{
    private SessionFactory sessionFactory;
    private DAOUtils daoUtils;

    @Override
    public void addVisit(Visit visit) {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(visit);
        transaction.commit();
        session.close();
    }

    public void deleteVisit(long visitId) {
        daoUtils.delete(sessionFactory, visitId, Visit.class);
    }

    @Override
    public void updateVisit(long visitId, Visit updater) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Visit visit = session.load(Visit.class, visitId);
        System.out.println(visit.toString() );
        transaction.commit();
        transaction = session.beginTransaction();
        visit.setClient(updater.getClient());
        visit.setLocker(updater.getLocker());
        visit.setFinish(updater.getFinish());
        visit.setStart(updater.getStart());
        session.update(visit);
        transaction.commit();
        System.out.println(visit.toString() );
        session.close();
    }

    @Override
    public Visit getVisitById(long visitId) {
        return (Visit) daoUtils.getById(sessionFactory, visitId, Visit.class);
    }

    @Override
    public Visit findActiveVisitForLocker(long id) {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query getVisit = session.createQuery
                ("from Visit v where v.locker.id = :id and v.finish is null")
                .setBigInteger("id", BigInteger.valueOf(id));
        Visit visit = (Visit) getVisit.uniqueResult();
        System.out.println(visit.toString());
        transaction.commit();
        if (session.isOpen()) session.close();
        return visit;
    }

    private void ifSessionFactoryIsNull() {
        if (null == sessionFactory)
            System.out.println(":(");
        sessionFactory = new ClassPathXmlApplicationContext("spring/root-config.xml").getBean(SessionFactory.class);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
