package com.github.shevchuk.clients.visit.dao;

import com.github.shevchuk.clients.visit.model.Visit;

/**
 * Created by dmsh0216 on 13/03/2017.
 */
public interface DAOVisit {
    void addVisit(Visit visit);
    void deleteVisit(long visitId);

    void updateVisit(long visitId, Visit updater);

    Visit getVisitById(long visitId);
}
