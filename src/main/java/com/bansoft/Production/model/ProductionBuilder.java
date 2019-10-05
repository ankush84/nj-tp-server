package com.bansoft.Production.model;

import java.time.Instant;

public class ProductionBuilder implements IProductionBuilder {

    private ProductionModel ProductionModel;

    public ProductionBuilder() {
        this.ProductionModel = new ProductionModel();
        ProductionModel.setId(0L);
    }
    @Override
    public IProductionBuilder stockId(Long stockId) {
        this.ProductionModel.setStockId(stockId);

        return this;
    }

    @Override
    public IProductionBuilder purchaseId(Long purchaseId) {
        this.ProductionModel.setPurchaseId(purchaseId);
        return this;
    }

    @Override
    public IProductionBuilder productName(String productName) {
        this.ProductionModel.setProductName(productName);
        return this;
    }

    @Override
    public IProductionBuilder qtyUsed(Double qtyUsed) {
        this.ProductionModel.setQtyUsed(qtyUsed);
        return this;
    }

    @Override
    public IProductionBuilder qtyWaste(Double qtyWaste) {
        this.ProductionModel.setQtyWaste(qtyWaste);
        return this;
    }

    @Override
    public IProductionBuilder details(String details) {
        this.ProductionModel.setDetails(details);
        return this;
    }

    @Override
    public IProductionBuilder lotNumber(String lotNumber) {
        this.ProductionModel.setLotNumber(lotNumber);
        return this;
    }

    
    @Override
    public IProductionBuilder price(Double price) {
        this.ProductionModel.setPrice(price);
        return this;
    }

    @Override
    public IProductionBuilder timestamp(Instant dt) {
        this.ProductionModel.setTimestamp(dt);
        return this;
    }

    @Override
    public IProduction build() {
        return ProductionModel;
    }
}