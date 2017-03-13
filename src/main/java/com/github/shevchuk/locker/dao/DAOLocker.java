package com.github.shevchuk.locker.dao;


import com.github.shevchuk.locker.model.Locker;

public interface DAOLocker {
    void addLocker(Locker locker);
    void removeLocker(Locker locker);
    void updateLocker(Locker locker);
    Locker getLockerById(long lockerId);
}
