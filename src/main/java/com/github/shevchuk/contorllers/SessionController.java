package com.github.shevchuk.contorllers;

import com.github.shevchuk.clients.client.dao.DAOClient;
import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.clients.visit.dao.DAOVisit;
import com.github.shevchuk.clients.visit.dto.VisitDTO;
import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.clients.visit.service.VisitService;
import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
public class SessionController {
    @Autowired
    private DAOLocker daoLocker;

    @Qualifier("simpleDAOClient")
    @Autowired
    private DAOClient daoClient;

    @Autowired
    @Qualifier("simpleDAOVisit")
    private DAOVisit daoVisit;

    @Autowired
    private VisitService visitService;

    @RequestMapping(value = "/lockers/reserved", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> getReservedLockers(){
        return daoLocker.getReservedLockers();
    }

    @RequestMapping(value = "/lockers/reserved/neighbors", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Locker> getNeighborsForReservedLockers(){
        Collection<Locker> inappropriateLockers = new HashSet<>();
        getInappropriateLockers().forEach(l -> inappropriateLockers.addAll(l.getNeighbors()));
        return inappropriateLockers;
    }

    @RequestMapping(value = "/lockers/inappropriate", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Locker> getInappropriateLockers(){
        return daoLocker.getInappropriateLockers();
    }

    @RequestMapping(value = "/lockers/all", method = RequestMethod.GET)
    public List<Locker> getAllLockers(){
        return daoLocker.getLockers();
    }

    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    public Client getClientById(@PathVariable("id") long id){
        return daoClient.getClientById(id);
    }

    @RequestMapping(value = "/visit/", method = RequestMethod.POST)
    public void newVisit(@RequestBody VisitDTO visitDTO){
        Visit visit = visitService.visitFromDTO(visitDTO);
        daoVisit.addVisit(visit);
    }


    public void setLockerDAO(DAOLocker daoLocker) {
        this.daoLocker = daoLocker;
    }

    public void setDaoClient(DAOClient daoClient) {
        this.daoClient = daoClient;
    }

    public VisitService getVisitService() {
        return visitService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }
}
