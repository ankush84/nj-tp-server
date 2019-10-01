package com.bansoft.Stock;

import java.time.Instant;

import com.bansoft.Stock.comm.StockSupply;
import com.bansoft.Stock.dal.StockEntity;
import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.StockBuilder;

public class StockConverter {

    public static StockEntity fromStockModelToEntity(IStock model) {
        StockEntity pe = new StockEntity();
        pe.setPurchaseId(model.getPurchaseId());
        pe.setQty(model.getQty());
        pe.setTimestamp(model.getTimestamp().toEpochMilli());

        return pe;
    }

    public static IStock fromStockEntityToModel(StockEntity entity) {
        StockBuilder builder = new StockBuilder();
        builder.purchaseId(entity.getPurchaseId());
        builder.qty(entity.getQty());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static StockSupply fromStockModelToSupply(IStock model) {
        StockSupply ps = new StockSupply();
        ps.purchaseId = model.getPurchaseId();
        ps.qty = model.getQty();
        ps.timestamp = model.getTimestamp().toEpochMilli();
        return ps;
    }
}