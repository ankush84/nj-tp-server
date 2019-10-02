//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Sale.comm;

import com.bansoft.Sale.ISaleService;
import com.bansoft.Sale.SaleConverter;
import com.bansoft.Sale.model.ISale;
import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class SaleTopic extends Topic {
    ISaleService SaleService;

    public SaleTopic(ISaleService SaleService) {
        super("Sale");
        this.SaleService = SaleService;
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter) {
        ISale[] Sales = SaleService.getAllSales();
        if (Sales != null && Sales.length > 0) {
            supplyBegin();
            for (ISale Sale : Sales) {
                supplyAdd(SaleConverter.fromSaleModelToSupply(Sale));
            }
            supplyEnd();
        }
    }
}