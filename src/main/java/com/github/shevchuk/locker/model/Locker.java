package com.github.shevchuk.locker.model;

import com.github.shevchuk.clients.visit.model.Visit;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "lockers")
public class Locker {

    @Id
    @Column(name = "locker_id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lockerId;

    @Column(name = "number")
    private int number;

//    @Transient
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="lockers_neighbors"
            , joinColumns={@JoinColumn(name="locker_id")}
            , inverseJoinColumns={@JoinColumn(name="neighbor_id")})
    private List<Locker> neighbors;


    @ManyToMany(mappedBy="neighbors")
    private List<Locker> lockers;

    @OneToOne(mappedBy = "locker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "locker_id")
    private Visit visit;


    public long getLockerId() {
        return lockerId;
    }

    public void setLockerId(long lockerId) {
        this.lockerId = lockerId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Locker> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Locker> neighbors) {
        this.neighbors = neighbors;
    }


    public void update(Locker locker){
        this.number = locker.getNumber();
        this.neighbors = locker.getNeighbors();
    }

    public List<Locker> getLockers() {
        return lockers;
    }

    public void setLockers(List<Locker> lockers) {
        this.lockers = lockers;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }
}
