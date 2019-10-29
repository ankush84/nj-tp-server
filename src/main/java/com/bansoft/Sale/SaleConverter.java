package com.bansoft.Sale;

import java.time.Instant;

import com.bansoft.Sale.comm.SaleSupply;
import com.bansoft.Sale.dal.SaleEntity;
import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.SaleBuilder;

public class SaleConverter {

    public static SaleEntity fromSaleModelToEntity(ISale model) {
        SaleEntity pe = new SaleEntity();
        pe.setDetails(model.getDetails());
        pe.setLotNumber(model.getLotNumber());
        pe.setParty(model.getParty());
        pe.setProductName(model.getProductName());
        pe.setBillNo(model.getBillNo());
        pe.setQtyUsed(model.getQtyUsed());
        pe.setSaleNo(model.getSaleNo());
        pe.setTimestamp(model.getTimestamp().toEpochMilli());

        return pe;
    }

    public static ISale fromSaleEntityToModel(SaleEntity entity) {
        SaleBuilder builder = new SaleBuilder();
        builder.details(entity.getDetails());
        builder.lotNumber(entity.getLotNumber());
        builder.party(entity.getParty());
        builder.productName(entity.getProductName());
        builder.billNo(entity.getBillNo());
        builder.qtyUsed(entity.getQtyUsed());
        builder.saleNumber(entity.getSaleNo());
        builder.timestamp(Instant.ofEpochMilli(entity.getTimestamp()));

        return builder.build();
    }

    public static SaleSupply fromSaleModelToSupply(ISale model) {
        SaleSupply ps = new SaleSupply();
        ps.details=model.getDetails();
        ps.id= model.getId();
        ps.lotNumber= model.getLotNumber();
        ps.productName= model.getProductName();
        ps.party= model.getParty();
        ps.billNo = model.getBillNo();
        ps.qtyUsed = model.getQtyUsed();
        ps.saleNo = model.getSaleNo();
        ps.timestamp = model.getTimestamp().toEpochMilli();
        return ps;
    }
}