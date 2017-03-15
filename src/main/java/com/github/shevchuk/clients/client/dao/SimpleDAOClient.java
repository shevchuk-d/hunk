package com.github.shevchuk.clients.client.dao;

import com.github.shevchuk.clients.client.model.Client;

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
        daoUtils.delete(sessionFactory, clientId, Client.class);
    }

    @Override
    public void updateClient(long clientId, Client updater) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Client clientPersistent = session.load(Client.class, clientId);
        clientPersistent.update(updater);
        session.update(clientPersistent);
        transaction.commit();
        session.close();
    }

    @Override
    public Client getClientById(long clientId) {
        return (Client) daoUtils.getById(sessionFactory, clientId, Client.class);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setDaoUtils(DAOUtils daoUtils) {
        this.daoUtils = daoUtils;
    }
}
