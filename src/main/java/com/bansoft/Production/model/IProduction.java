package com.bansoft.Production.model;

import java.time.Instant;

public interface IProduction{
    Long getId();
    Long getStockId();
    Long getPurchaseId();
    String getProductName();
    Double getQtyUsed();
    Double getQtyWaste();
    String getLotNumber();
    String getDetails();
    Instant getTimestamp();

    void setId(Long id);
    void setStockId(Long stockId);
    void setPurchaseId(Long purchaseId);
    void setProductName(String productName);
    void setQtyUsed(Double qtyUsed);
    void setQtyWaste(Double qtyWaste);
    void setLotNumber(String lotNumber);
    void setDetails(String details);
    void setTimestamp(Instant timestamp);
}