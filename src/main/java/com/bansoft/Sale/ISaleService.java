package com.bansoft.Sale;

import java.time.Instant;
import java.util.LinkedList;

import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.ISaleBuilder;
import com.bansoft.Sale.model.SaleJob;

public interface ISaleService {
    
    public ISaleBuilder newSale();

    public void commitSale(ISale Sale);
    
    public ISale getSaleById(Long id);

    public ISale[] getAllSales();

	public void produce(String finalProductName,String lotNumber, String details, Instant timeInstant, LinkedList<SaleJob> SaleJobs);

    //public IEvent<ISale> SaleAddedEvent();
}


