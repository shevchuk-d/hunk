package com.github.shevchuk.clients.client.dao;

import com.github.shevchuk.clients.client.model.Client;


public interface DAOClient {
    void addClient(Client client);
    void removeClient(Client client);
    void updateClient(Client client);
    Client getClientById(long clientId);
}
