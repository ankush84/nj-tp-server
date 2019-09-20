//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Purchase;

import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class PurchaseTopic extends Topic {
    PurchaseService purchaseService;

    public PurchaseTopic(PurchaseService purchaseService) {
        super("Purchase");
        this.purchaseService = purchaseService;
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter) {
        IPurchase[] purchases = purchaseService.getAllPurchases();
        if (purchases != null && purchases.length > 0) {
            supplyBegin();
            for (IPurchase purchase : purchases) {
                supplyAdd(PurchaseConverter.fromPurchaseModelToSupply(purchase));
            }
            supplyEnd();
        }
    }
}