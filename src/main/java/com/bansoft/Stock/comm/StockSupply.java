package com.bansoft.Stock.comm;

public class StockSupply {
     public Long id;
     public Long purchaseId;
	public String productName;
     public Double qty;     //duplicate should come from purchase?
     public Double price;     //duplicate should come from purchase?
     public long timestamp; //availablesince this time
}