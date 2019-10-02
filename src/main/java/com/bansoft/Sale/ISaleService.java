package com.bansoft.Sale;

import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.ISaleBuilder;

public interface ISaleService {
    public ISaleBuilder newSale();

    public void commitSale(ISale Sale);
    
    public ISale getSaleById(Long id);

    public ISale[] getAllSales();

    //public IEvent<ISale> SaleAddedEvent();
}


