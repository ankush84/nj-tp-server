//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Purchase.comm;

import java.time.Instant;
import java.util.HashMap;

import com.bansoft.Purchase.IPurchaseService;
import com.bansoft.Purchase.model.IPurchaseBuilder;
import com.bansoft.comm.Request;
import com.bansoft.comm.payload.ReplyMessage;

public class AddPurchaseRequest extends Request {
    IPurchaseService purchaseService;

    public AddPurchaseRequest(IPurchaseService purchaseService) {
        super("AddPurchase");
        this.purchaseService = purchaseService;
    }
  
    @Override
    protected ReplyMessage getReply(HashMap<String, Object> args) {
        IPurchaseBuilder builder=  purchaseService.newPurchase();
        
        builder.billingId(args.get("billingId").toString());
        builder.details(args.get("details").toString());
        builder.price(Double.valueOf(args.get("price").toString()));
        builder.productName(args.get("productName").toString());
        builder.qty(Double.valueOf(args.get("qty").toString()));
        builder.timestamp(Instant.now());
        
        purchaseService.commitPurchase(builder.build());
        ReplyMessage rm = new ReplyMessage();
        rm.returnCode=0;
        return rm;        
    }
}