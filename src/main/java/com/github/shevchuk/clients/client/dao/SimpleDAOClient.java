package com.github.shevchuk.clients.client.dao;

import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.client.model.SimpleClient;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;



@Repository
public class SimpleDAOClient implements DAOClient {

    private SessionFactory sessionFactory;

    @Override
    public void addClient(Client client) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
//        session.persist(client);
        session.save(client);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeClient(Client client) {
        sessionFactory.getCurrentSession().delete(client);
    }

    @Override
    public void updateClient(Client client) {
        Session session = sessionFactory.getCurrentSession();
        session.update(client);
        session.close();
    }

    @Override
    public Client getClientById(long clientId) {
        return sessionFactory.getCurrentSession().load(SimpleClient.class, clientId);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
