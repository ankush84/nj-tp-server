//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.util.HashMap;

import com.bansoft.comm.payload.RequestMessage;

public class RequestService {

    private HashMap<String, Request> requestMap;

    private static class BillPughSingleton {
        private static final RequestService INSTANCE = new RequestService();
    }

    public static RequestService getInstance() {
        return BillPughSingleton.INSTANCE;
    }

    private RequestService() {
        requestMap = new HashMap<>();
    }

    public void registerRequest(Request req) {
        if (!requestMap.containsKey(req.getName())) {
            requestMap.put(req.getName(),req);
        }
    }

    public void getReply(MessagingAdapter messagingAdapter, RequestMessage rm) {
        if (requestMap.containsKey(rm.name)) {
            requestMap.get(rm.name).getReply(messagingAdapter, rm);
        }
    }
}