//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Stock.comm;

import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.StockConverter;
import com.bansoft.Stock.model.IStock;
import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class StockTopic extends Topic {
    IStockService StockService;

    public StockTopic(IStockService StockService) {
        super("Stock");
        this.StockService = StockService;
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter) {
        IStock[] Stocks = StockService.getAllStocks();
        if (Stocks != null && Stocks.length > 0) {
            supplyBegin();
            for (IStock Stock : Stocks) {
                supplyAdd(StockConverter.fromStockModelToSupply(Stock));
            }
            supplyEnd();
        }
    }
}