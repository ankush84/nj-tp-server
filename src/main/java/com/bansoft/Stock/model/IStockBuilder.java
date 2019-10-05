package com.bansoft.Stock.model;

import java.time.Instant;

public interface IStockBuilder{
    IStockBuilder Id(Long id);
    IStockBuilder productName(String productName);    
    IStockBuilder purchaseId(Long purchaseId);    
    IStockBuilder qty(Double qty);
    IStockBuilder price(Double price);
    IStockBuilder timestamp(Instant dt);
    IStock build();
}