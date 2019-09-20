package com.bansoft.Purchase;

public interface IPurchaseService {
    public IPurchaseBuilder newPurchase();

    public void commitPurchase(IPurchase purchase);
    
    public IPurchase getPurchaseById(Long id);

    public IPurchase[] getAllPurchases();

    //public IEvent<IPurchase> purchaseAddedEvent();
}


