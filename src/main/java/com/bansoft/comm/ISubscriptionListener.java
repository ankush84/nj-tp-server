package com.bansoft.comm;

public interface ISubscriptionListener{
    void supplyBegin();
    void supplyAdd(Object supply);
    void supplyUpdate(Object supply);
    void supplyRemove(Object supply);
    void supplyEnd();
}