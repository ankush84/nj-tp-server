package com.bansoft.Production.model;

import com.bansoft.Stock.model.IStock;

public class ProductionJob {

    private String productName;
    private Double qtyUsed;
    private Double qtyWaste;
    private IStock[] stocks;

    public ProductionJob(String productName, Double qtyUsed, Double qtyWaste, IStock[] stocks) {
        super();
        this.productName = productName;
        this.qtyUsed = qtyUsed;
        this.qtyWaste = qtyWaste;
        this.stocks = stocks;
    }

    public String getProductName() {
        return productName;
    }

    public Double getQtyUsed() {
        return qtyUsed;
    }

    public Double getQtyWaste() {
        return qtyWaste;
    }

    public IStock[] getStocks() {
        return stocks;
    }
}