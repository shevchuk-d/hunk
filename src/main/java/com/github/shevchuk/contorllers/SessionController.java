package com.github.shevchuk.contorllers;

import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
public class SessionController {
    @Autowired
    private DAOLocker daoLocker;


    @RequestMapping(value = "/lockers/reserved", method = RequestMethod.GET)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> getReservedLockers(){
        return daoLocker.getReservedLockers();
    }

    @RequestMapping(value = "/lockers/reserved/neighbors", method = RequestMethod.GET)
//    @Path("/lockers/reserved/neighbors")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Collection<Locker> getNeighborsForReservedLockers(){
        Collection<Locker> inappropriateLockers = new HashSet<>();
        getInappropriateLockers().forEach(l -> inappropriateLockers.addAll(l.getNeighbors()));
        return inappropriateLockers;
    }

    @GET
    @Path("/lockers/inappropriate")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Collection<Locker> getInappropriateLockers(){
        return daoLocker.getInappropriateLockers();
    }

    @RequestMapping(value = "/lockers/all", method = RequestMethod.GET)
    public List<Locker> getAllLockers(){
        return daoLocker.getLockers();
    }

    @GET
    @Path("/size")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public int getReservedLockersQuantity(){
        return daoLocker.getReservedLockers().size();
    }

    @GET
    @Path("/locker/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getLockerById(@PathParam("id") long id){
        return Response.ok().entity(daoLocker.getLockerById(id)).build();
    }


    @GET
    @Path("/locker/{id}/neighbors")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getNeighborsById(@PathParam("id") long id){
        return Response.ok().entity(daoLocker.getNeighborsById(id)).build();
    }

    public void setLockerDAO(DAOLocker daoLocker) {
        this.daoLocker = daoLocker;
    }

}
