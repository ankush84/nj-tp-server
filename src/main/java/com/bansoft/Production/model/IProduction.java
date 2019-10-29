package com.bansoft.Production.model;

import java.time.Instant;

public interface IProduction{
    Long getId();
    Long getStockId();
    Long getPurchaseId();
    String getProductName();
	String getFinalProductName();
    Double getQtyUsed();
    Double getQtyWaste();
    Double getPrice();
    String getLotNumber();
    String getDetails();
    Instant getTimestamp();

    void setId(Long id);
    void setStockId(Long stockId);
    void setPurchaseId(Long purchaseId);
    void setProductName(String productName);
    void setFinalProductName(String finalProductName);
    void setQtyUsed(Double qtyUsed);
    void setQtyWaste(Double qtyWaste);
    void setPrice(Double price);
    void setLotNumber(String lotNumber);
    void setDetails(String details);
    void setTimestamp(Instant timestamp);
}