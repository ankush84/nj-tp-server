//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.ProductionStock.comm;

import com.bansoft.ProductionStock.IProductionStockService;
import com.bansoft.ProductionStock.ProductionStockConverter;
import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class ProductionStockTopic extends Topic {
    IProductionStockService ProductionStockService;

    public ProductionStockTopic(IProductionStockService ProductionStockService) {
        super("ProductionStock");
        this.ProductionStockService = ProductionStockService;
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter) {
        IProductionStock[] ProductionStocks = ProductionStockService.getAllProductionStocks();
        if (ProductionStocks != null && ProductionStocks.length > 0) {
            supplyBegin();
            for (IProductionStock ProductionStock : ProductionStocks) {
                supplyAdd(ProductionStockConverter.fromProductionStockModelToSupply(ProductionStock));
            }
            supplyEnd();
        }
    }
}