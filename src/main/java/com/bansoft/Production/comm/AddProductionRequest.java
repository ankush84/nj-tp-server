//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Production.comm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.bansoft.Production.IProductionService;
import com.bansoft.Production.model.ProductionJob;
import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.model.IStock;
import com.bansoft.comm.Request;
import com.bansoft.comm.payload.ReplyMessage;

public class AddProductionRequest extends Request {
    private IProductionService productionService;
    private IStockService stockService;

    public AddProductionRequest(IProductionService productionService, IStockService stockService) {
        super("AddProduction");
        this.productionService = productionService;
        this.stockService = stockService;
    }

    @Override
    protected ReplyMessage getReply(HashMap<String, Object> args) {

        ArrayList<String> productNames = (ArrayList<String>) args.get("productNames");
        ArrayList<String> qtyUsedAry = (ArrayList<String>) args.get("qtyUsed");
        ArrayList<String> qtyWasteAry = (ArrayList<String>) args.get("qtyWaste");
        String finalProductName = args.get("finalProductName").toString();
        //String lotNumber = args.get("lotNumber").toString();
        String details = args.get("details").toString();
        Instant timeInstant = Instant.now();
        LinkedList<ProductionJob> productionJobs = new LinkedList<ProductionJob>();
        LinkedList<String> outOfStock = new LinkedList<String>();

        for (int i = 0; i < productNames.size(); i++) {
            String productName = productNames.get(i);
            Double qtyUsed = Double.valueOf(qtyUsedAry.get(i));
            Double qtyWaste = Double.valueOf(qtyWasteAry.get(i));

            Double requiredQty = qtyUsed + qtyWaste;
            IStock[] stocks = stockService.takeOutStocksForProduct(productName, requiredQty);

            if (stocks == null) {
                outOfStock.add(productName);
            } else {
                productionJobs.add(new ProductionJob(productName, qtyUsed, qtyWaste, stocks));
            }

        }

        if (outOfStock.size() > 0) {
            for (ProductionJob job : productionJobs) {
                stockService.restoreStocks(job.getProductName(), job.getStocks());
            }
            ReplyMessage rm = new ReplyMessage();
            rm.returnCode = -1;
            rm.returnValues = new String[] { "Out of stock." };
            // outOfStock.stream().collect(collector);
            return rm;
        }

        //now we commit in transit stocks.
        for (ProductionJob job : productionJobs) {
            for (IStock stock : job.getStocks()) {
                stockService.consumeStock(stock);
            }
        }
        //if we fail in production, we waste our stock, we should notify user
        productionService.produce(finalProductName,null,details,timeInstant, productionJobs);

        ReplyMessage rm = new ReplyMessage();
        rm.returnCode = 0;
        return rm;
    }
}