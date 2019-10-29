package com.bansoft.ProductionStock.model;

import java.time.Instant;

public class ProductionStockBuilder implements IProductionStockBuilder{

    private ProductionStockModel ProductionStockModel;
    public ProductionStockBuilder() {
        this.ProductionStockModel=new ProductionStockModel();
        //ProductionStockModel.setId(0L);
    }

    @Override
    public IProductionStockBuilder Id(Long id) {
       this.ProductionStockModel.setId(id);
        return this;
    }
    
    @Override
    public IProductionStockBuilder lotNumber(String id) {
       this.ProductionStockModel.setLotNumber(id);
        return this;
    }

    @Override
    public IProductionStockBuilder qty(Double qty) {
        this.ProductionStockModel.setQty(qty);
        return this;
    }

    @Override
    public IProductionStockBuilder cost(Double cost) {
        this.ProductionStockModel.setCost(cost);
        return this;
    }

    @Override
    public IProductionStockBuilder timestamp(Instant dt) {
        this.ProductionStockModel.setTimestamp(dt);
        return this;
    }

    @Override
    public IProductionStock build() {                
        return ProductionStockModel;
    }

    @Override
    public IProductionStockBuilder productName(String productName) {
        this.ProductionStockModel.setProductName(productName);
        return this;
    }
        
}