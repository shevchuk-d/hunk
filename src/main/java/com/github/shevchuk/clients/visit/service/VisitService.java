package com.github.shevchuk.clients.visit.service;

import com.github.shevchuk.clients.client.dao.DAOClient;
import com.github.shevchuk.clients.visit.dao.DAOVisit;
import com.github.shevchuk.clients.visit.dto.VisitDTO;
import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.locker.dao.DAOLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class VisitService {

    @Autowired
    private DAOLocker daoLocker;

    @Qualifier("simpleDAOClient")
    @Autowired
    private DAOClient daoClient;

    @Autowired
    @Qualifier("simpleDAOVisit")
    private DAOVisit daoVisit;

    public Visit visitFromDTO(VisitDTO visitDTO){
        Visit visit = new Visit();
        visit.setLocker(visitDTO.getLocker() == 0 ? null : daoLocker.getLockerById(visitDTO.getLocker()));
        visit.setClient(visitDTO.getClient() == 0 ? null : daoClient.getClientById(visitDTO.getClient()));
        visit.setStart(visitDTO.getStart());
        visit.setFinish(visitDTO.getFinish());
        return visit;
    }

    public DAOVisit getDaoVisit() {
        return daoVisit;
    }

    public void setDaoVisit(DAOVisit daoVisit) {
        this.daoVisit = daoVisit;
    }

    public DAOLocker getDaoLocker() {
        return daoLocker;
    }

    public void setDaoLocker(DAOLocker daoLocker) {
        this.daoLocker = daoLocker;
    }

    public DAOClient getDaoClient() {
        return daoClient;
    }

    public void setDaoClient(DAOClient daoClient) {
        this.daoClient = daoClient;
    }
}
