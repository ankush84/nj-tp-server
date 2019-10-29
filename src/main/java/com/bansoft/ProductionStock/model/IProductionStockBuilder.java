package com.bansoft.ProductionStock.model;

import java.time.Instant;

public interface IProductionStockBuilder{
    IProductionStockBuilder Id(Long id);
    IProductionStockBuilder productName(String productName);    
    IProductionStockBuilder lotNumber(String lotNumber);    
    IProductionStockBuilder qty(Double qty);
    IProductionStockBuilder cost(Double cost);
    IProductionStockBuilder timestamp(Instant dt);
    IProductionStock build();
}