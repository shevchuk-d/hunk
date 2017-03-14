package com.github.shevchuk.clients.client.dao;

import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.client.model.SimpleClient;

import com.github.shevchuk.utils.DAOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


@Repository
public class SimpleDAOClient implements DAOClient {

    private SessionFactory sessionFactory;
    private DAOUtils daoUtils;

    @Override
    public void addClient(Client client) {
        daoUtils.add(sessionFactory, client);
    }

    @Override
    public void deleteClient(long clientId) {
        daoUtils.delete(sessionFactory, clientId, SimpleClient.class);
    }

    @Override
    public void updateClient(long clientId, Client updater) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Client clientPersistent = session.load(SimpleClient.class, clientId);
        clientPersistent.update(updater);
        session.update(clientPersistent);
        transaction.commit();
        session.close();
    }

    @Override
    public Client getClientById(long clientId) {
        return (SimpleClient) daoUtils.getById(sessionFactory, clientId, SimpleClient.class);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
