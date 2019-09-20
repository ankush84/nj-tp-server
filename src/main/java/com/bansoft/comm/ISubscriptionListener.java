package com.bansoft.comm;

public interface ISubscriptionListener{
    void supplyBegin();
    void supplyAdd(String supply);
    void supplyUpdate(String supply);
    void supplyRemove(String supply);
    void supplyEnd();
}