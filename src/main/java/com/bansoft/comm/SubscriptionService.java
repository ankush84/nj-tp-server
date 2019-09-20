//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.util.HashMap;

public class SubscriptionService {

    private HashMap<String, HashMap<String, ISubscriptionListener>> topicToListenerMap;

    private static class BillPughSingleton {
        private static final SubscriptionService INSTANCE = new SubscriptionService();
    }

    public static SubscriptionService getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    private SubscriptionService() {
        topicToListenerMap = new HashMap<>();
    }

    public void addSubscription(String topic, MessagingAdapter messagingAdapter) {
        if (topicToListenerMap.containsKey(topic)) {
            if (!topicToListenerMap.get(topic).containsKey(messagingAdapter.getSessionId())) {
                topicToListenerMap.get(topic).put(messagingAdapter.getSessionId(),
                        new SubscrptionListener(messagingAdapter));
            }
        } else {
            HashMap<String, ISubscriptionListener> listeners = new HashMap<String, ISubscriptionListener>();
            topicToListenerMap.put(topic, listeners);
            listeners.put(messagingAdapter.getSessionId(), new SubscrptionListener(messagingAdapter));
        }
    }

    public void removeSubscription(String topic, MessagingAdapter messagingAdapter) {
        if (topicToListenerMap.containsKey(topic)) {
            if (topicToListenerMap.get(topic).containsKey(messagingAdapter.getSessionId())) {
                topicToListenerMap.get(topic).remove(messagingAdapter.getSessionId());
            }
        }
    }

    public void removeAllSubscription(MessagingAdapter messagingAdapter) {
        for (String topic : topicToListenerMap.keySet()) {
            removeSubscription(topic, messagingAdapter);
        }
    }

    public void supplyBegin(String topic) {
        if (topicToListenerMap.containsKey(topic)) {
            HashMap<String, ISubscriptionListener> listeners = topicToListenerMap.get(topic);
            for (ISubscriptionListener listener : listeners.values()) {
                listener.supplyBegin();
            }
        }
    }

    public void supplyAdd(String topic, String supply) {
        if (topicToListenerMap.containsKey(topic)) {
            HashMap<String, ISubscriptionListener> listeners = topicToListenerMap.get(topic);
            for (ISubscriptionListener listener : listeners.values()) {
                listener.supplyBegin();
                listener.supplyAdd(supply);
                listener.supplyEnd();
            }
        }
    }

    public void supplyUpdate(String topic, String supply) {
        if (topicToListenerMap.containsKey(topic)) {
            HashMap<String, ISubscriptionListener> listeners = topicToListenerMap.get(topic);
            for (ISubscriptionListener listener : listeners.values()) {
                listener.supplyUpdate(supply);
            }
        }
    }

    public void supplyRemove(String topic, String supply) {
        if (topicToListenerMap.containsKey(topic)) {
            HashMap<String, ISubscriptionListener> listeners = topicToListenerMap.get(topic);
            for (ISubscriptionListener listener : listeners.values()) {
                listener.supplyRemove(supply);
            }
        }
    }

    public void supplyEnd(String topic) {
        if (topicToListenerMap.containsKey(topic)) {
            HashMap<String, ISubscriptionListener> listeners = topicToListenerMap.get(topic);
            for (ISubscriptionListener listener : listeners.values()) {
                listener.supplyEnd();
            }
        }
    }

}