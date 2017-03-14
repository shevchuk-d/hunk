package com.github.shevchuk.locker.model;

import java.util.List;

/**
 * Created by dmsh0216 on 13/03/2017.
 */
public interface Locker {
    long getLockerId();

    void setLockerId(long lockerId);

    int getNumber();

    void setNumber(int number);

    List<LockerSingleton> getNeighbors();

    void setNeighbors(List<LockerSingleton> neighbors);

    void update(LockerSingleton locker);
}
