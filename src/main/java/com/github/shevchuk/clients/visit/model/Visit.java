package com.github.shevchuk.clients.visit.model;


import java.util.Date;

public interface Visit {
    long getVisitId();

    void setVisitId(long visitId);

    Date getStart();

    void setStart(Date start);

    Date getFinish();

    void setFinish(Date finish);

    int getLockerId();

    void setLockerId(int lockerId);

    int getClientId();

    void setClientId(int clientId);

    void update(Visit visit);
}
