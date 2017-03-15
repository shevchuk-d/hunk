package com.github.shevchuk.clients.client.model;

import com.github.shevchuk.clients.visit.model.Visit;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "client_id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @Column(name = "name")
    private String name;

    @Column(name = "sex")
    private String sex;

    @OneToMany(mappedBy = "client")
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

    public void update(Client client){
        this.name = client.getName();
        this.sex = client.getSex();
    }
}
