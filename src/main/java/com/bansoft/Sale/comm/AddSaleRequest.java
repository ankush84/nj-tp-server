//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Sale.comm;

import java.time.Instant;
import java.util.HashMap;

import com.bansoft.Sale.ISaleService;
import com.bansoft.Sale.model.ISaleBuilder;
import com.bansoft.comm.Request;
import com.bansoft.comm.payload.ReplyMessage;

public class AddSaleRequest extends Request {
    ISaleService SaleService;

    public AddSaleRequest(ISaleService SaleService) {
        super("AddSale");
        this.SaleService = SaleService;
    }
  
    @Override
    protected ReplyMessage getReply(HashMap<String, Object> args) {
        ISaleBuilder builder=  SaleService.newSale();
        
        builder.billingId(args.get("billingId").toString());
        builder.details(args.get("details").toString());
        builder.price(Double.valueOf(args.get("price").toString()));
        builder.productName(args.get("productName").toString());
        builder.qty(Double.valueOf(args.get("qty").toString()));
        builder.timestamp(Instant.now());
        
        SaleService.commitSale(builder.build());
        ReplyMessage rm = new ReplyMessage();
        rm.returnCode=0;
        return rm;        
    }
}