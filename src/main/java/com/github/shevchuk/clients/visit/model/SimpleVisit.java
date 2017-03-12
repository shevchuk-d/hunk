package com.github.shevchuk.clients.visit.model;

import com.github.shevchuk.locker.model.Locker;

import javax.persistence.Entity;
import java.util.Date;


@Entity
public class SimpleVisit {
    private long visitId;
    private Date start;
    private Date finish;
    private Locker locker;

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

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }
}
