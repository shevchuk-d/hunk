package com.github.shevchuk.locker.dao;

import com.github.shevchuk.locker.model.Locker;
import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.*;

import org.springframework.context.support.ClassPathXmlApplicationContext;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
    public Collection<Locker> getInappropriateLockers() {
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query getInappropriateNeighborsLockers = session.createSQLQuery
                ("SELECT l.locker_id, l.number\n" +
                        "FROM visits v\n" +
                        "JOIN lockers l\n" +
                        "  ON v.locker_id = l.locker_id\n" +
                        "JOIN clients c\n" +
                        "  ON v.client_id = c.client_id\n" +
                        "WHERE finish ISNULL\n" +
                        "AND v.start NOTNULL\n" +
                        "AND (current_timestamp\n" +
                        "  BETWEEN\n" +
                        "    v.start\n" +
                        "      AND\n" +
                        "    v.start + INTERVAL '2 minutes'\n" +
                        "or current_timestamp >=\n" +
                        "    v.start + (SELECT avg(finish - start)\n" +
                        "                 FROM visits\n" +
                        "                 WHERE visits.client_id = c.client_id\n" +
                        "    ) - INTERVAL '2 minutes')\n" +
                        ";").addEntity(Locker.class);
        List<Locker> lockers = (List<Locker>)getInappropriateNeighborsLockers.list();
        transaction.commit();
        if (session.isOpen()) session.close();
        return lockers;
    }

    private Date getAverageVisitTime(){
        ifSessionFactoryIsNull();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query getAverageVisitTime = session.createQuery
                ("select avg(v.finish - v.start) from Visit v group by v.client");
        Date averageVisitTime = (Date) getAverageVisitTime.uniqueResult();
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
