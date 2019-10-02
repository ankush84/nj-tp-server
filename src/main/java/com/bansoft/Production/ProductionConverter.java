package com.bansoft.Production;

import java.time.Instant;

import com.bansoft.Production.comm.ProductionSupply;
import com.bansoft.Production.dal.ProductionEntity;
import com.bansoft.Production.model.IProduction;
import com.bansoft.Production.model.ProductionBuilder;

public class ProductionConverter {

    public static ProductionEntity fromProductionModelToEntity(IProduction model) {
        ProductionEntity pe = new ProductionEntity();
        pe.setDetails(model.getDetails());
        pe.setLotNumber(model.getLotNumber());
        pe.setPurchaseId(model.getPurchaseId());
        pe.setQtyUsed(model.getQtyUsed());
        pe.setQtyWaste(model.getQtyWaste());
        pe.setTimestamp(model.getTimestamp().toEpochMilli());

        return pe;
    }

    public static IProduction fromProductionEntityToModel(ProductionEntity entity) {
        ProductionBuilder builder = new ProductionBuilder();
        builder.details(entity.getDetails());
        builder.lotNumber(entity.getLotNumber());
        builder.purchaseId(entity.getPurchaseId());
        builder.qtyUsed(entity.getQtyUsed());
        builder.qtyWaste(entity.getQtyWaste());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static ProductionSupply fromProductionModelToSupply(IProduction model) {
        ProductionSupply ps = new ProductionSupply();
        ps.details=model.getDetails();
        ps.id= model.getId();
        ps.lotNumer= model.getLotNumber();
        ps.purchaseId = model.getPurchaseId();
        ps.qtyUsed = model.getQtyUsed();
        ps.qtyWaste = model.getQtyWaste();
        ps.timestamp = model.getTimestamp().toEpochMilli();
        return ps;
    }
}