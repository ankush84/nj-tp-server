package com.bansoft.Stock.model;

import java.time.Instant;

public interface IStockBuilder{
    IStockBuilder purchaseId(Long purchaseId);    
    IStockBuilder qty(Double qty);
    IStockBuilder timestamp(Instant dt);
    IStock build();
}