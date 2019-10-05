package com.bansoft.Stock;

import java.time.Instant;

import com.bansoft.Stock.comm.StockSupply;
import com.bansoft.Stock.dal.StockEntity;
import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.StockBuilder;

public class StockConverter {

    public static StockEntity fromStockModelToEntity(IStock model) {
        StockEntity entity = new StockEntity();
        entity.setId(model.getId());
        entity.setProductName(model.getProductName());
        entity.setPurchaseId(model.getPurchaseId());
        entity.setQty(model.getQty());
        entity.setPrice(model.getPrice());
        entity.setTimestamp(model.getTimestamp().toEpochMilli());
        return entity;
    }

    public static IStock fromStockEntityToModel(StockEntity entity) {
        StockBuilder builder = new StockBuilder();
        builder.Id(entity.getId());
        builder.productName(entity.getProductName());
        builder.purchaseId(entity.getPurchaseId());
        builder.qty(entity.getQty());
        builder.price(entity.getPrice());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static StockSupply fromStockModelToSupply(IStock model) {
        StockSupply supply = new StockSupply();
        supply.id = model.getId();
        supply.productName = model.getProductName();
        supply.purchaseId = model.getPurchaseId();
        supply.qty = model.getQty();
        supply.price = model.getPrice();
        supply.timestamp = model.getTimestamp().toEpochMilli();
        return supply;
    }
}