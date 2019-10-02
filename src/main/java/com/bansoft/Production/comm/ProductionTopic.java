//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Production.comm;

import com.bansoft.Production.IProductionService;
import com.bansoft.Production.ProductionConverter;
import com.bansoft.Production.model.IProduction;
import com.bansoft.comm.MessagingAdapter;
import com.bansoft.comm.Topic;

public class ProductionTopic extends Topic {
    IProductionService ProductionService;

    public ProductionTopic(IProductionService ProductionService) {
        super("Production");
        this.ProductionService = ProductionService;
    }

    @Override
    protected void supplyCurrentCache(MessagingAdapter messagingAdapter) {
        IProduction[] Productions = ProductionService.getAllProductions();
        if (Productions != null && Productions.length > 0) {
            supplyBegin();
            for (IProduction Production : Productions) {
                supplyAdd(ProductionConverter.fromProductionModelToSupply(Production));
            }
            supplyEnd();
        }
    }
}