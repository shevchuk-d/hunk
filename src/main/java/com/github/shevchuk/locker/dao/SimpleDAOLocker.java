package com.github.shevchuk.locker.dao;

import com.github.shevchuk.locker.model.Locker;
import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.SessionFactory;


public class SimpleDAOLocker implements DAOLocker {
    private SessionFactory sessionFactory;
    private DAOUtils daoUtils;

    @Override
    public void addLocker(Locker locker) {
        sessionFactory.getCurrentSession().save(locker);
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
        return (Locker) sessionFactory.getCurrentSession().load(Locker.class, lockerId);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
