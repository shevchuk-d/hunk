package com.github.shevchuk.locker.dao;

import com.github.shevchuk.locker.model.Locker;
import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.joda.time.DateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class SimpleDAOLocker implements DAOLocker {
    private SessionFactory sessionFactory;

    private DAOUtils daoUtils;

    @Override
    public void addLocker(Locker locker) {
        daoUtils.add(sessionFactory, locker);
    }

    @Override
    public void removeLocker(Locker locker) {
        sessionFactory.getCurrentSession().delete(locker);
    }

    @Override
    public void updateLocker(Locker locker) {
        sessionFactory.getCurrentSession().update(locker);
    }

    @Override
    public Locker getLockerById(long lockerId) {
        ifSessionFactoryIsNull();
        return sessionFactory.openSession().load(Locker.class, lockerId);
    }

    @Override
    public List<Locker> getReservedLockers(){
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery
                ("select l from Visit as v " +
                        "join v.locker l " +
                        "where v.start is not null " +
                        "and v.finish is null");
        List<Locker> lockers = q.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }


    @Override
    public List<Locker> getLockers(){
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery
                ("from Locker");
        List<Locker> lockers = q.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }

    @Override
    public List<Locker> getNeighborsById(long id) {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery
                ("select neighbors from Locker l join l.lockers neighbors where l.id = :id").setParameter("id", id);
        List<Locker> lockers = q.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }

    @Override
    public List<Locker> getNeighborsForReservedLockers() {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery
                ("select n from Visit as v " +
                        "join v.locker l " +
                        "join l.neighbors n " +
                        "where v.start is not null " +
                        "and v.finish is null");
        List<Locker> lockers = q.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }

    @Override
    public List<Locker> getAppropriateLockers() {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query getInappropriateNeighborsLockers = session.createQuery
                ("select n from Visit as v " +
                    "join v.locker l " +
                    "join l.neighbors n " +
                "where v.start is not null " +
                    "and v.finish is null " +
                    "and (  " +
                        "( current_timestamp > 1  " +
                        "and current_timestamp < v.start )  " +
                    "or " +
                        "( current_timestamp > :start " +
                        "and current_timestamp < :estimatedFinish) " +
                    ")"
                );
        getInappropriateNeighborsLockers.setParameter("estimatedFinish", new DateTime().plusHours(2).minusMinutes(15).toDate());
        getInappropriateNeighborsLockers.setParameter("start", new DateTime().plusMinutes(15).toDate());
        List<Locker> lockers = getInappropriateNeighborsLockers.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }

    private Date getAverageVisitTime(){
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Query getAverageVisitTime = session.createQuery
                ("select avg(v.finish - v.start) from Visit v group by v.client");
        Date averageVisitTime = (Date) getAverageVisitTime.uniqueResult();
        Transaction transaction = session.beginTransaction();
        transaction.commit();
        if (session.isOpen()) session.close();
        return averageVisitTime;
    }


    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }

    public DAOUtils getDaoUtils() {
        return daoUtils;
    }

    private void ifSessionFactoryIsNull(){
        if (null == sessionFactory)
            sessionFactory = new ClassPathXmlApplicationContext("spring/root-config.xml").getBean(SessionFactory.class);
    }
}
