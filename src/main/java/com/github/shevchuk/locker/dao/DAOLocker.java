package com.github.shevchuk.locker.dao;


import com.github.shevchuk.locker.model.Locker;

import java.util.List;

public interface DAOLocker {
    void addLocker(Locker locker);
    void removeLocker(Locker locker);
    void updateLocker(Locker locker);
    Locker getLockerById(long lockerId);

    List<Locker> getReservedLockers();

    List<Locker> getLockers();

    Object getNeighborsById(long id);

    List<Locker> getNeighborsForReservedLockers();
}
