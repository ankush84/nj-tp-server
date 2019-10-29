package com.bansoft.Sale.model;

import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.Stock.model.IStock;

public class SaleJob {

    private String productName;
    private Double qtyUsed;
    private IProductionStock[] stocks;

    public SaleJob(String productName, Double qtyUsed, IProductionStock[] stocks) {
        super();
        this.productName = productName;
        this.qtyUsed = qtyUsed;
        this.stocks = stocks;
    }

    public String getProductName() {
        return productName;
    }

    public Double getQtyUsed() {
        return qtyUsed;
    }

  
    public IProductionStock[] getStocks() {
        return stocks;
    }
}