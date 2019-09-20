package com.bansoft.Purchase;

import java.time.Instant;

import com.bansoft.dal.hibernate.entities.PurchaseEntity;

public class PurchaseConverter {

    public static PurchaseEntity fromPurchaseModelToEntity(IPurchase model) {
        PurchaseEntity pe = new PurchaseEntity();
        pe.setBillingId(model.getBillingId());
        pe.setDetails(model.getDetails());
        pe.setPrice(model.getPrice());
        pe.setProductName(model.getProductName());
        pe.setQty(model.getQty());
        pe.setTimestamp(model.getTimestamp().toEpochMilli());

        return pe;
    }

    public static IPurchase fromPurchaseEntityToModel(PurchaseEntity entity) {
        PurchaseBuilder builder = new PurchaseBuilder();
        builder.billingId(entity.getBillingId());
        builder.details(entity.getDetails());
        builder.price(entity.getPrice());
        builder.productName(entity.getProductName());
        builder.qty(entity.getQty());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

}