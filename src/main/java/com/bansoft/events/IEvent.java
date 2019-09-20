package com.bansoft.events;

public interface IEvent<Targs>{

    void add(IEventObserver<Targs> observer);
    void remove(IEventObserver<Targs> observer);
}