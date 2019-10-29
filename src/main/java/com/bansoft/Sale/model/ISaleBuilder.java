package com.bansoft.Sale.model;

import java.time.Instant;

public interface ISaleBuilder{
    ISaleBuilder productName(String productName);
    ISaleBuilder qtyUsed(Double qtyUsed);
    ISaleBuilder lotNumber(String lotNumber); 
    ISaleBuilder details(String details); 
    ISaleBuilder timestamp(Instant dt);
    ISale build();
	ISaleBuilder party(String party);
	ISaleBuilder billNo(String billNumber);
	ISaleBuilder saleNumber(Long saleNumber);
}