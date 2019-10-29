package com.bansoft.ProductionStock;

import java.time.Instant;

import com.bansoft.ProductionStock.comm.ProductionStockSupply;
import com.bansoft.ProductionStock.dal.ProductionStockEntity;
import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.ProductionStock.model.ProductionStockBuilder;

public class ProductionStockConverter {

    public static ProductionStockEntity fromProductionStockModelToEntity(IProductionStock model) {
        ProductionStockEntity entity = new ProductionStockEntity();
        entity.setId(model.getId());
        entity.setProductName(model.getProductName());
        entity.setLotNumber(model.getLotNumber());
        entity.setQty(model.getQty());
        entity.setCost(model.getCost());
        entity.setTimestamp(model.getTimestamp().toEpochMilli());
        return entity;
    }

    public static IProductionStock fromProductionStockEntityToModel(ProductionStockEntity entity) {
        ProductionStockBuilder builder = new ProductionStockBuilder();
        builder.Id(entity.getId());
        builder.productName(entity.getProductName());
        builder.lotNumber(entity.getLotNumber());
        builder.qty(entity.getQty());
        builder.cost(entity.getCost());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static ProductionStockSupply fromProductionStockModelToSupply(IProductionStock model) {
        ProductionStockSupply supply = new ProductionStockSupply();
        supply.id = model.getId();
        supply.productName = model.getProductName();
        supply.lotNumber = model.getLotNumber();
        supply.qty = model.getQty();
        supply.cost = model.getCost();
        supply.timestamp = model.getTimestamp().toEpochMilli();
        return supply;
    }
}