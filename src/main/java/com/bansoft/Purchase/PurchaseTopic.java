//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Purchase;

import java.util.HashMap;

import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;
import com.bansoft.dal.hibernate.HibernateService;

public class PurchaseTopic extends Topic {
    PurchaseService purchaseService;
    
    public PurchaseTopic(HibernateService hibernateService) {
        super("Purchase");
        purchaseService = new PurchaseService(hibernateService);
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter){
        IPurchase[] purchases = purchaseService.getAllPurchases();
        supplyBegin();
        for (IPurchase purchase : purchases) {
            supplyAdd(PurchaseConverter.fromPurchaseModelToSupply(purchase));
        }
        supplyEnd();
    }
}