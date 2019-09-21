package com.bansoft.Purchase.model;

import java.time.Instant;

public class PurchaseBuilder implements IPurchaseBuilder{

    private PurchaseModel purchaseModel;
    public PurchaseBuilder() {
        this.purchaseModel=new PurchaseModel();
        purchaseModel.setId(0L);
    }

    @Override
    public IPurchaseBuilder productName(String name) {
       this.purchaseModel.setProductName(name);
        return this;
    }

    @Override
    public IPurchaseBuilder price(Double price) {
        this.purchaseModel.setPrice(price);
        return this;
    }

    @Override
    public IPurchaseBuilder qty(Double qty) {
        this.purchaseModel.setQty(qty);
        return this;
    }

    @Override
    public IPurchaseBuilder details(String details) {
        this.purchaseModel.setDetails(details);
        return this;
    }

    @Override
    public IPurchaseBuilder billingId(String billingId) {
        this.purchaseModel.setBillingId(billingId);
        return this;
    }

    @Override
    public IPurchaseBuilder timestamp(Instant dt) {
        this.purchaseModel.setTimestamp(dt);
        return this;
    }

    @Override
    public IPurchase build() {                
        return purchaseModel;
    }
        
}