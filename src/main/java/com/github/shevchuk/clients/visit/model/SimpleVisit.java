package com.github.shevchuk.clients.visit.model;

import com.github.shevchuk.locker.model.LockerSingleton;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "visits")
public class SimpleVisit implements Visit {
    @Id
    @Column(name = "visit_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitId;
    @Column(name = "start")
    private Date start;
    @Column(name = "finish")
    private Date finish;
    @Column(name = "locker_id")
    private int lockerId;
    @Column(name = "client_id")
    private int clientId;

    @Override
    public long getVisitId() {
        return visitId;
    }
    @Override
    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }
    @Override
    public Date getStart() {
        return start;
    }
    @Override
    public void setStart(Date start) {
        this.start = start;
    }
    @Override
    public Date getFinish() {
        return finish;
    }
    @Override
    public void setFinish(Date finish) {
        this.finish = finish;
    }
    @Override
    public int getLockerId() {
        return lockerId;
    }
    @Override
    public void setLockerId(int lockerId) {
        this.lockerId = lockerId;
    }
    @Override
    public int getClientId() {
        return clientId;
    }
    @Override
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public void update(Visit visit){
        this.start = visit.getStart();
        this.finish = visit.getFinish();
        this.lockerId = visit.getLockerId();
        this.clientId = visit.getClientId();
    }
}
