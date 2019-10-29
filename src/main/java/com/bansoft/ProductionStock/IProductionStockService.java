package com.bansoft.ProductionStock;

import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.ProductionStock.model.IProductionStockBuilder;

public interface IProductionStockService {
    public IProductionStockBuilder newProductionStock();
    public void commitProductionStock(IProductionStock ProductionStock);
    public IProductionStock[] getAllProductionStocks();
    //IProductionStock getProductionStockByPurchaseId(Long id);
	public IProductionStock[] takeOutProductionStocksForProduct(String productName, Double requiredQty);
	public void restoreProductionStocks(String productName, IProductionStock[] ProductionStocks);
    //public IEvent<IProductionStock> ProductionStockAddedEvent();
	public void consumeProductionStock(IProductionStock ProductionStock);
}


