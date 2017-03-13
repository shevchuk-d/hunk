package com.github.shevchuk.locker.model;

import javax.persistence.*;
import java.util.List;


@Entity(name = "Lockers")
public class LockerSingleton implements Locker {

    @Id
    @Column(name = "locker_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lockerId;

    @Column(name = "number")
    private int number;

    @Transient
    private List<Locker> neighbors;

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
}
