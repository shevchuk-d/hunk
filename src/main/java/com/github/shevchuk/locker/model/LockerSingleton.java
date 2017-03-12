package com.github.shevchuk.locker.model;

import javax.persistence.Entity;
import java.util.List;


@Entity(name = "Lockers")
public class LockerSingleton implements Locker {
    private long lockerId;
    private int lockerNumber;
    private List<Locker> neighbors;

    public long getLockerId() {
        return lockerId;
    }

    public void setLockerId(long lockerId) {
        this.lockerId = lockerId;
    }

    public int getLockerNumber() {
        return lockerNumber;
    }

    public void setLockerNumber(int lockerNumber) {
        this.lockerNumber = lockerNumber;
    }

    public List<Locker> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Locker> neighbors) {
        this.neighbors = neighbors;
    }
}
