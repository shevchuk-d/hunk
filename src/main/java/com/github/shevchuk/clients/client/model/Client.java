package com.github.shevchuk.clients.client.model;


import com.github.shevchuk.clients.visit.model.Visit;

import java.util.List;

public interface Client {

    long getClientId();
    void setClientId(long clientId);

    String getName();
    void setName(String name);

    String getSex();
    void setSex(String sex);

    List<Visit> getVisits();
    void setVisits(List<Visit> visits);

    Visit getAverageVisit();
    void setAverageVisit(Visit averageVisit);

    void update(Client client);
}
