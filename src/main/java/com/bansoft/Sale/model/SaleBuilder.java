package com.bansoft.Sale.model;

import java.time.Instant;

public class SaleBuilder implements ISaleBuilder{

    private SaleModel SaleModel;
    public SaleBuilder() {
        this.SaleModel=new SaleModel();
        SaleModel.setId(0L);
    }

    @Override
    public ISaleBuilder productName(String name) {
       this.SaleModel.setProductName(name);
        return this;
    }

    @Override
    public ISaleBuilder price(Double price) {
        this.SaleModel.setPrice(price);
        return this;
    }

    @Override
    public ISaleBuilder qty(Double qty) {
        this.SaleModel.setQty(qty);
        return this;
    }

    @Override
    public ISaleBuilder details(String details) {
        this.SaleModel.setDetails(details);
        return this;
    }

    @Override
    public ISaleBuilder billingId(String billingId) {
        this.SaleModel.setBillingId(billingId);
        return this;
    }

    @Override
    public ISaleBuilder timestamp(Instant dt) {
        this.SaleModel.setTimestamp(dt);
        return this;
    }

    @Override
    public ISale build() {                
        return SaleModel;
    }
        
}