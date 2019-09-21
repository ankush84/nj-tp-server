//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Purchase.comm;

import com.bansoft.Purchase.IPurchaseService;
import com.bansoft.Purchase.PurchaseConverter;
import com.bansoft.Purchase.model.IPurchase;
import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class PurchaseTopic extends Topic {
    IPurchaseService purchaseService;

    public PurchaseTopic(IPurchaseService purchaseService) {
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