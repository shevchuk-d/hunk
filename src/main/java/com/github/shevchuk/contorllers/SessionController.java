package com.github.shevchuk.contorllers;

import com.github.shevchuk.locker.dao.DAOLocker;
import com.github.shevchuk.locker.model.Locker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/hello")
@RestController
public class SessionController {
    @Autowired
    private DAOLocker daoLocker;

    @GET
    @Path("/world")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Locker> startSession(){
        return daoLocker.getReservedLockers();
    }


    @GET
    @Path("/worldI")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public int startSessionI(){
        return daoLocker.getReservedLockers().size();
    }

    @GET
    @Path("/world/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response startSessionZ(@PathParam("id") long id){
        return Response.ok().entity(daoLocker.getLockerById(id)).build();
    }



    public void setLockerDAO(DAOLocker daoLocker) {
        this.daoLocker = daoLocker;
    }

    //    static List<Locker> lockers;
//
//    static {
//        lockers = new ArrayList<>();
//
//        Locker locker0 = new Locker();
//        locker0.setNumber(0);
//
//        Locker locker1 = new Locker();
//        locker1.setNumber(1);
//        Locker locker2 = new Locker();
//        locker2.setNumber(2);
//        Locker locker3 = new Locker();
//        locker3.setNumber(3);
//        Locker locker4 = new Locker();
//        locker3.setNumber(4);
//
//        lockers.add(locker1);
//        lockers.add(locker2);
//        lockers.add(locker3);
//        lockers.add(locker4);
//        locker0.setNeighbors(lockers);
//    }

//    @Autowired
//    @Qualifier(value = "lockerDAO2")
//    private SimpleDAOLocker lockerDAO;
}
