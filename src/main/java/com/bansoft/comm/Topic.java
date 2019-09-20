//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.util.HashMap;

public abstract class Topic {

    private String name;
    private HashMap<String, ISubscriptionListener> listenersMap;

    public Topic(String name) {
        this.name=name;
        listenersMap = new HashMap<>();
        SubscriptionService.getInstance().registerTopic(this);
    }

    public String getName() {
        return name;
    }

    public void addSubscription(MessagingAdapter messagingAdapter) {
        if (!listenersMap.containsKey(messagingAdapter.getSessionId())) {
            listenersMap.put(messagingAdapter.getSessionId(),
                    new SubscrptionListener(messagingAdapter));
            supplyCurrentCache(messagingAdapter);
        }
    }

    public void removeSubscription(MessagingAdapter messagingAdapter) {
            if (listenersMap.containsKey(messagingAdapter.getSessionId())) {
                listenersMap.remove(messagingAdapter.getSessionId());
            }       
    }
    
    protected void supplyBegin() {
        for (ISubscriptionListener listener : listenersMap.values()) {
            listener.supplyBegin();
        }
    }

    protected void supplyAdd(Object supply) {
        for (ISubscriptionListener listener : listenersMap.values()) {
            listener.supplyAdd(supply);
        }
    }

    protected void supplyUpdate(Object supply) {
        for (ISubscriptionListener listener : listenersMap.values()) {
            listener.supplyUpdate(supply);
        }
    }
    
    protected void supplyRemove(Object supply) {
        for (ISubscriptionListener listener : listenersMap.values()) {
            listener.supplyRemove(supply);
        }
    }

    protected void supplyEnd() {
        for (ISubscriptionListener listener : listenersMap.values()) {
            listener.supplyEnd();
        }
    }

    protected abstract void supplyCurrentCache(MessagingAdapter messagingAdapter);
}