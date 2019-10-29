package com.bansoft.Sale.model;

import java.time.Instant;

public class SaleBuilder implements ISaleBuilder {

    private SaleModel SaleModel;

    public SaleBuilder() {
        this.SaleModel = new SaleModel();
        SaleModel.setId(0L);
    }
    
    @Override
    public ISaleBuilder productName(String productName) {
        this.SaleModel.setProductName(productName);
        return this;
    }

    @Override
    public ISaleBuilder qtyUsed(Double qtyUsed) {
        this.SaleModel.setQtyUsed(qtyUsed);
        return this;
    }


    @Override
    public ISaleBuilder details(String details) {
        this.SaleModel.setDetails(details);
        return this;
    }

    @Override
    public ISaleBuilder lotNumber(String lotNumber) {
        this.SaleModel.setLotNumber(lotNumber);
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

    @Override
    public ISaleBuilder party(String party) {
        this.SaleModel.setParty(party);
        return this;
    }

    @Override
    public ISaleBuilder billNo(String billNumber) {
        this.SaleModel.setBillNo(billNumber);
        return this;
    }

    @Override
    public ISaleBuilder saleNumber(Long saleNumber) {
        this.SaleModel.setSaleNo(saleNumber);
        return this;
    }
}