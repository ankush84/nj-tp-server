package com.bansoft.Stock.model;

import java.time.Instant;

public interface IStock{
    Long getId();
    Long getPurchaseId();
    Double getQty();
    Instant getTimestamp();

    void setId(Long id);
    void setPurchaseId(Long id);
    void setQty(Double qty);
    void setTimestamp(Instant timestamp);
}