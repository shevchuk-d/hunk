package com.github.shevchuk.clients.client.model;

import com.github.shevchuk.clients.visit.model.Visit;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "clients")
public class SimpleClient implements Client {
    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

//    @OneToMany
//    @JoinTable(name = "",
//            joinColumns = @JoinColumn(name = "client_id")
//            , inverseJoinColumns = @JoinColumn(name = "visit_id"))
    @Transient
    private List<Visit> visits;
    @Transient
    private Visit averageVisit;

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public Visit getAverageVisit() {
        return averageVisit;
    }

    public void setAverageVisit(Visit averageVisit) {
        this.averageVisit = averageVisit;
    }
}
