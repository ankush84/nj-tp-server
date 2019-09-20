package com.bansoft.Purchase;

import java.time.Instant;

public interface IPurchase{
    Long getId();
    String getProductName();
    Double getPrice();
    Double getQty();
    String getBillingId();
    String getDetails();
    Instant getTimestamp();

    void setId(Long id);
    void setProductName(String productName);
    void setPrice(Double price);
    void setQty(Double qty);
    void setBillingId(String details);
    void setDetails(String details);
    void setTimestamp(Instant timestamp);
}