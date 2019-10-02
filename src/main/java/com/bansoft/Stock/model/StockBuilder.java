package com.bansoft.Stock.model;

import java.time.Instant;

public class StockBuilder implements IStockBuilder{

    private StockModel StockModel;
    public StockBuilder() {
        this.StockModel=new StockModel();
        //StockModel.setId(0L);
    }

    @Override
    public IStockBuilder Id(Long id) {
       this.StockModel.setId(id);
        return this;
    }
    
    @Override
    public IStockBuilder purchaseId(Long id) {
       this.StockModel.setPurchaseId(id);
        return this;
    }

    @Override
    public IStockBuilder qty(Double qty) {
        this.StockModel.setQty(qty);
        return this;
    }

    @Override
    public IStockBuilder timestamp(Instant dt) {
        this.StockModel.setTimestamp(dt);
        return this;
    }

    @Override
    public IStock build() {                
        return StockModel;
    }

    @Override
    public IStockBuilder productName(String productName) {
        this.StockModel.setProductName(productName);
        return this;
    }
        
}