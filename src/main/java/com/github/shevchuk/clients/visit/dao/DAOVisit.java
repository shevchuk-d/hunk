package com.github.shevchuk.clients.visit.dao;

import com.github.shevchuk.clients.visit.model.Visit;

/**
 * Created by dmsh0216 on 13/03/2017.
 */
public interface DAOVisit {
    void addVisit(Visit visit);
    void removeVisit(Visit visit);
    void updateVisit(Visit visit);
    Visit getVisitById(long visitId);
}
