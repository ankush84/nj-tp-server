package com.bansoft.Purchase;

import com.bansoft.Purchase.model.IPurchase;
import com.bansoft.Purchase.model.IPurchaseBuilder;

public interface IPurchaseService {
    public IPurchaseBuilder newPurchase();

    public void commitPurchase(IPurchase purchase);
    
    public IPurchase getPurchaseById(Long id);

    public IPurchase[] getAllPurchases();

    //public IEvent<IPurchase> purchaseAddedEvent();
}


