package com.github.shevchuk.clients.visit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.shevchuk.clients.client.model.Client;
import com.github.shevchuk.locker.model.Locker;
import com.sun.deploy.util.SessionState;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @Column(name = "visit_id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long visitId;
    @Column(name = "start")
    private Date start;
    @Column(name = "finish")
    private Date finish;
//    @Column(name = "locker_id")
//    private int lockerId;


    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Override
    public String toString() {
        return "ID: " + visitId +
                "\nStarted: " + start +
                "\nFinished: " +  ( null == finish ? "Not yet" : finish ) +
                "\nClient " + client.getName();
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

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
