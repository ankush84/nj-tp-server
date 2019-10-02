package com.bansoft.Sale;

import java.time.Instant;

import com.bansoft.Sale.comm.SaleSupply;
import com.bansoft.Sale.dal.SaleEntity;
import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.SaleBuilder;

public class SaleConverter {

    public static SaleEntity fromSaleModelToEntity(ISale model) {
        SaleEntity pe = new SaleEntity();
        pe.setBillingId(model.getBillingId());
        pe.setDetails(model.getDetails());
        pe.setPrice(model.getPrice());
        pe.setProductName(model.getProductName());
        pe.setQty(model.getQty());
        pe.setTimestamp(model.getTimestamp().toEpochMilli());

        return pe;
    }

    public static ISale fromSaleEntityToModel(SaleEntity entity) {
        SaleBuilder builder = new SaleBuilder();
        builder.billingId(entity.getBillingId());
        builder.details(entity.getDetails());
        builder.price(entity.getPrice());
        builder.productName(entity.getProductName());
        builder.qty(entity.getQty());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static SaleSupply fromSaleModelToSupply(ISale model) {
        SaleSupply ps = new SaleSupply();
        ps.billingId=model.getBillingId();
        ps.details= model.getDetails();
        ps.price= model.getPrice();
        ps.productName = model.getProductName();
        ps.qty = model.getQty();
        ps.timestamp = model.getTimestamp().toEpochMilli();
        return ps;
    }
}