package com.github.shevchuk.contorllers;

import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/session")
@RestController
public class SessionController {
    @Autowired
    private DAOLocker daoLocker;

    @GET
    @Path("/lockers/reserved")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> getReservedLockers(){
        return daoLocker.getReservedLockers();
    }

    @GET
    @Path("/lockers/reserved/neighbors")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> getNeighborsForReservedLockers(){
        return daoLocker.getNeighborsForReservedLockers();
    }

    @GET
    @Path("/lockers/appropriate")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> getAppropriateLockers(){
        return daoLocker.getAppropriateLockers();
    }

    @GET
    @Path("/lockers/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
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
