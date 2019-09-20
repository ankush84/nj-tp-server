package com.bansoft.events;

public interface IEventObserver<Targs>{
    void observe(Object event , Targs args);
}