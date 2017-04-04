package com.github.shevchuk.clients.visit.dto;

import com.github.shevchuk.locker.model.Locker;

import java.util.Date;

public class VisitDTO {

    private long visitId;

    private Date start;

    private Date finish;

    private long locker;

    private long client;

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
