//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.util.HashMap;

public class SubscriptionService {

    private HashMap<String, Topic> topicsMap;

    private static class BillPughSingleton {
        private static final SubscriptionService INSTANCE = new SubscriptionService();
    }

    public static SubscriptionService getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    private SubscriptionService() {
        topicsMap = new HashMap<>();
    }

    public void registerTopic(Topic topic) {
        if (!topicsMap.containsKey(topic.getName())) {
            topicsMap.put(topic.getName(),topic);
        }
    }

    public void addSubscription(String topic, MessagingAdapter messagingAdapter) {
        if (topicsMap.containsKey(topic)) {
            topicsMap.get(topic).addSubscription(messagingAdapter);
        }
    }

    public void removeSubscription(String topic, MessagingAdapter messagingAdapter) {
        if (topicsMap.containsKey(topic)) {
            topicsMap.get(topic).removeSubscription(messagingAdapter);
        }
    }

    public void removeAllSubscription(MessagingAdapter messagingAdapter) {
        for (String topic : topicsMap.keySet()) {
            removeSubscription(topic, messagingAdapter);
        }
    }

    

}