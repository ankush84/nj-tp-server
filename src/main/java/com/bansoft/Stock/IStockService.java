package com.bansoft.Stock;

import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.IStockBuilder;

public interface IStockService {
    public IStockBuilder newStock();
    public void commitStock(IStock Stock);
    public IStock[] getAllStocks();
    IStock getStockByPurchaseId(Long id);
	public IStock[] takeOutStocksForProduct(String productName, Double requiredQty);
	public void restoreStocks(String productName, IStock[] stocks);
    //public IEvent<IStock> StockAddedEvent();
	public void consumeStock(IStock stock);
}


