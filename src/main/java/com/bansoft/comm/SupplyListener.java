//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import com.bansoft.comm.payload.Data;
import com.bansoft.comm.payload.SupplyMessage;
import com.google.gson.Gson;

public class SupplyListener implements ISubscriptionListener{

    private MessagingAdapter messagingAdapter;
    private Gson gson;
    private String topic;

    public SupplyListener(MessagingAdapter messagingAdapter, String topic) {

        this.messagingAdapter = messagingAdapter;
        this.topic = topic;
        this.gson = messagingAdapter.getGson();
    }

    @Override
    public void supplyBegin() {       
        SupplyMessage supplyMessage = new SupplyMessage();
        supplyMessage.phase = SupplyMessage.BATCH_BEGIN;
        send(supplyMessage);
    }

    @Override
    public void supplyAdd(Object supply) {        
        SupplyMessage supplyMessage = new SupplyMessage();
        supplyMessage.phase = SupplyMessage.ADD;
        supplyMessage.supply = gson.toJson(supply);
        send(supplyMessage);      
    }   

    @Override
    public void supplyUpdate(Object supply) {
        SupplyMessage supplyMessage = new SupplyMessage();
        supplyMessage.phase = SupplyMessage.UPDATE;
        supplyMessage.supply = gson.toJson(supply);
        send(supplyMessage);
    }

    @Override
    public void supplyRemove(Object supply) {
        SupplyMessage supplyMessage = new SupplyMessage();
        supplyMessage.phase = SupplyMessage.DELETE;
        supplyMessage.supply = gson.toJson(supply);
        send(supplyMessage);
    }

    @Override
    public void supplyEnd() {        
        SupplyMessage supplyMessage = new SupplyMessage();
        supplyMessage.phase = SupplyMessage.BATCH_END;
        send(supplyMessage);
    }

    private void send(SupplyMessage supplyMessage) {
        supplyMessage.topic=this.topic;
        Data data= new Data();
        data.operation = Data.OPERATION_SUPPLY;
        data.sessionId=messagingAdapter.getSessionId();
        data.message= gson.toJson(supplyMessage);
        messagingAdapter.sendStringToRemote(gson.toJson(data));        
    }
}