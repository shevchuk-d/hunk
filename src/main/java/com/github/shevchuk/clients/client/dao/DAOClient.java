package com.github.shevchuk.clients.client.dao;

import com.github.shevchuk.clients.client.model.Client;


public interface DAOClient {
    void addClient(Client client);

    void deleteClient(long clientId);

    void updateClient(long clientId, Client update);

    Client getClientById(long clientId);
}
