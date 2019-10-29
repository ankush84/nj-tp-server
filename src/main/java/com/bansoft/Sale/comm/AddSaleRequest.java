//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.Sale.comm;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.bansoft.ProductionStock.IProductionStockService;
import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.Sale.ISaleService;
import com.bansoft.Sale.model.SaleJob;
import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.model.IStock;
import com.bansoft.comm.Request;
import com.bansoft.comm.payload.ReplyMessage;

public class AddSaleRequest extends Request {
    private ISaleService SaleService;
    private IProductionStockService stockService;

    public AddSaleRequest(ISaleService SaleService, IProductionStockService stockService) {
        super("AddSale");
        this.SaleService = SaleService;
        this.stockService = stockService;
    }

    @Override
    protected ReplyMessage getReply(HashMap<String, Object> args) {

        String productName =  args.get("productName").toString();
        String qtyUsed = args.get("qtyUsed").toString();
        String party = args.get("party").toString();
        String billNo = args.get("billNo").toString();
        String details = args.get("details").toString();
        Instant timeInstant = Instant.now();
        LinkedList<SaleJob> SaleJobs = new LinkedList<SaleJob>();
        LinkedList<String> outOfStock = new LinkedList<String>();

            Double requiredQty =Double.valueOf(qtyUsed);
            IProductionStock[] stocks = stockService.takeOutProductionStocksForProduct(productName, requiredQty);

            if (stocks == null) {
                outOfStock.add(productName);
            } else {
                SaleJobs.add(new SaleJob(productName, requiredQty, stocks));
            }

        if (outOfStock.size() > 0) {
            for (SaleJob job : SaleJobs) {
                stockService.restoreProductionStocks(job.getProductName(), job.getStocks());
            }
            ReplyMessage rm = new ReplyMessage();
            rm.returnCode = -1;
            rm.returnValues = new String[] { "Out of stock." };
            // outOfStock.stream().collect(collector);
            return rm;
        }

        //now we commit in transit stocks.
        for (SaleJob job : SaleJobs) {
            for (IProductionStock stock : job.getStocks()) {
                stockService.consumeProductionStock(stock);
            }
        }
        //if we fail in Sale, we waste our stock, we should notify user
        SaleService.produce(party,billNo, details,timeInstant, SaleJobs);

        ReplyMessage rm = new ReplyMessage();
        rm.returnCode = 0;
        return rm;
    }
}