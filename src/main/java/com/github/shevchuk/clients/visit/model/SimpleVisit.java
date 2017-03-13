package com.github.shevchuk.clients.visit.model;

import com.github.shevchuk.locker.model.LockerSingleton;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "visits")
public class SimpleVisit {
    @Id
    @Column(name = "visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitId;
    private Date start;
    private Date finish;
    @Column(name = "locker_id")
    private int lockerId;
    @Column(name = "client_id")
    private int clientId;

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


    public int getLockerId() {
        return lockerId;
    }

    public void setLockerId(int lockerId) {
        this.lockerId = lockerId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
