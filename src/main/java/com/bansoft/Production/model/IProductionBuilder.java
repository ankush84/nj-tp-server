package com.bansoft.Production.model;

import java.time.Instant;

public interface IProductionBuilder{
    IProductionBuilder stockId(Long stockId);
    IProductionBuilder purchaseId(Long purchaseId);
    IProductionBuilder productName(String productName);
    IProductionBuilder qtyUsed(Double qtyUsed);
    IProductionBuilder qtyWaste(Double qtyWaste);
    IProductionBuilder lotNumber(String lotNumber); 
    IProductionBuilder details(String details); 
    IProductionBuilder timestamp(Instant dt);
    IProduction build();
}