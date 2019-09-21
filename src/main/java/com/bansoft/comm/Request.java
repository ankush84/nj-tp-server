//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.util.HashMap;

import com.bansoft.comm.payload.Data;
import com.bansoft.comm.payload.ReplyMessage;
import com.bansoft.comm.payload.RequestMessage;

public abstract class Request {

    private String name;

    public Request(String name) {
        this.name = name;
        RequestService.getInstance().registerRequest(this);
    }

    public String getName() {
        return name;
    }

    public void getReply(MessagingAdapter messagingAdapter, RequestMessage requestMessage) {
        Data data = new Data();
        data.operation = Data.OPERATION_SUPPLY;
        data.sessionId = messagingAdapter.getSessionId();
        HashMap<String, Object> argMap = new HashMap<>();
        for (int i = 0; i < requestMessage.argNames.length; i++) {
            argMap.put(requestMessage.argNames[i], requestMessage.argValues[i]);
        }
        ReplyMessage rm = getReply(argMap);
        rm.id=requestMessage.id;
        rm.name=requestMessage.name;

        data.message = messagingAdapter.getGson().toJson(rm);
        messagingAdapter.sendStringToRemote(messagingAdapter.getGson().toJson(data));
    }

    protected abstract ReplyMessage getReply(HashMap<String, Object> args);
}