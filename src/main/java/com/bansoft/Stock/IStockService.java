package com.bansoft.Stock;

import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.IStockBuilder;

public interface IStockService {
    public IStockBuilder newStock();

    public void commitStock(IStock Stock);
    
    public IStock getStockById(Long id);

    public IStock[] getAllStocks();
    IStock getStockByPurchaseId(Long id);

    //public IEvent<IStock> StockAddedEvent();
}


