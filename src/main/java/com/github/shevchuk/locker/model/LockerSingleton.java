package com.github.shevchuk.locker.model;

import javax.persistence.*;
import java.util.List;


@Entity(name = "Lockers")
public class LockerSingleton implements Locker {

    @Id
    @Column(name = "locker_id", nullable = false, updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lockerId;

    @Column(name = "number")
    private int number;

//    @Transient
    @OneToMany(mappedBy = "lockerSingleton")
    private List<LockerSingleton> neighbors;

    @ManyToOne
    @JoinTable(name = "lockers_neighbors"
            , joinColumns = @JoinColumn(name = "neighbor_id")
            , inverseJoinColumns = @JoinColumn(name = "locker_id")
    )
    private LockerSingleton lockerSingleton;

    @Override
    public long getLockerId() {
        return lockerId;
    }
    @Override
    public void setLockerId(long lockerId) {
        this.lockerId = lockerId;
    }
    @Override
    public int getNumber() {
        return number;
    }
    @Override
    public void setNumber(int number) {
        this.number = number;
    }
    @Override
    public List<LockerSingleton> getNeighbors() {
        return neighbors;
    }
    @Override
    public void setNeighbors(List<LockerSingleton> neighbors) {
        this.neighbors = neighbors;
    }

    @Override
    public void update(LockerSingleton locker){
        this.number = locker.getNumber();
        this.neighbors = locker.getNeighbors();
    }

    public LockerSingleton getLockerSingleton() {
        return lockerSingleton;
    }

    public void setLockerSingleton(LockerSingleton lockerSingleton) {
        this.lockerSingleton = lockerSingleton;
    }
}
