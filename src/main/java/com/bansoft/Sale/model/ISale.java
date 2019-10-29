package com.bansoft.Sale.model;

import java.time.Instant;

public interface ISale{
    Long getId();
    String getProductName();
    Double getQtyUsed();
    String getLotNumber();
    String getDetails();
    String getBillNo();
    Long getSaleNo();
    String getParty();
    Instant getTimestamp();

    void setId(Long id);
    void setProductName(String productName);
    void setQtyUsed(Double qtyUsed);
    void setLotNumber(String lotNumber);
    void setDetails(String details);
    void setTimestamp(Instant timestamp);
    void setBillNo(String billNo);
    void setParty(String party);
    void setSaleNo(Long saleNo);

}