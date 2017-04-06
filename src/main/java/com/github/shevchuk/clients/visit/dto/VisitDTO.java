package com.github.shevchuk.clients.visit.dto;

import com.github.shevchuk.clients.visit.model.Visit;
import com.github.shevchuk.locker.model.Locker;

import java.util.Date;

public class VisitDTO {

    private long visitId;

    private Date start;

    private Date finish;

    private long locker;

    private long client;

    public VisitDTO(){}

    public VisitDTO(Visit visit){
        visitId = visit.getVisitId();
        start = visit.getStart();
        finish = visit.getFinish();
        locker = visit.getLocker().getLockerId();
        client = visit.getClient().getClientId();
    }

    @Override
    public String toString() {
        return "ID: " + visitId +
                "\nStarted: " + start +
                "\nFinished: " +  ( null == finish ? "Not yet" : finish ) +
                "\nLocker " + locker +
                "\nClient " + client;
    }

    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public long getLocker() {
        return locker;
    }

    public void setLocker(long locker) {
        this.locker = locker;
    }

    public long getClient() {
        return client;
    }

    public void setClient(long client) {
        this.client = client;
    }
}
