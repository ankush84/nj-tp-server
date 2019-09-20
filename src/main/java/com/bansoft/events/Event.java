package com.bansoft.events;

import java.util.LinkedList;


public class Event<Targs> implements IEvent<Targs>{

    private LinkedList<IEventObserver<Targs>> observers;
    public Event() {
        observers=new LinkedList<>();
    }

    @Override
    public void add(IEventObserver<Targs> observer){
        this.observers.add(observer);
    }

    @Override
    public void remove(IEventObserver<Targs> observer){
        this.observers.remove(observer);
    }

    public void raise(Targs args){        
        for (IEventObserver<Targs> observer : observers) {
           try{ observer.observe(this, args);
           }catch(Exception ex){
               ex.printStackTrace();               
           }
        }
    }
}