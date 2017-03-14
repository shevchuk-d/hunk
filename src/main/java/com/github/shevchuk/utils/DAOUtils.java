package com.github.shevchuk.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class DAOUtils {

    public void add(SessionFactory sessionFactory, Object object){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
        session.close();
    }

    public void delete(SessionFactory sessionFactory, long objectId, Class entityClass){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object clientPersistent = session.load(entityClass, objectId);
        session.delete(entityClass.cast(clientPersistent));
        transaction.commit();
        session.close();
    }

    public Object getById(SessionFactory sessionFactory, long objectId, Class entityClass){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Object client = session.load(entityClass, objectId);
        transaction.commit();
        session.close();
        return entityClass.cast(client);
    }
}
