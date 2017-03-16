package com.github.shevchuk.locker.dao;

import com.github.shevchuk.locker.model.Locker;
import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;


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
        return sessionFactory.getCurrentSession().load(Locker.class, lockerId);
    }

    @Override
    public List<Locker> getReservedLockers(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query q = session.createQuery
                ("select l from Visit as v join " +
                        "v.locker l where v.start is not null and v.finish is null");

        List<Locker> lockers = q.list();
        transaction.commit();
        session.close();
        return lockers;
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
}