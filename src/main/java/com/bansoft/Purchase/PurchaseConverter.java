package com.bansoft.Purchase;

import java.time.Instant;

import com.bansoft.Purchase.comm.PurchaseSupply;
import com.bansoft.Purchase.dal.PurchaseEntity;
import com.bansoft.Purchase.model.IPurchase;
import com.bansoft.Purchase.model.PurchaseBuilder;

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

    public static PurchaseSupply fromPurchaseModelToSupply(IPurchase model) {
        PurchaseSupply ps = new PurchaseSupply();
        ps.billingId=model.getBillingId();
        ps.details= model.getDetails();
        ps.price= model.getPrice();
        ps.productName = model.getProductName();
        ps.qty = model.getQty();
        ps.timestamp = model.getTimestamp().toEpochMilli();
        return ps;
    }
}