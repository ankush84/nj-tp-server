package com.bansoft.ProductionStock.model;

import java.time.Instant;

public interface IProductionStock{
    Long getId();
    String getLotNumber();
    String getProductName();
    Double getQty();
    Double getCost();
    Instant getTimestamp();

    void setId(Long id);
    void setProductName(String id);
    void setLotNumber(String id);
    void setQty(Double qty);
    void setCost(Double cost);
    void setTimestamp(Instant timestamp);
}