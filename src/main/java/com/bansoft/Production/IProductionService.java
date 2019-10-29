package com.bansoft.Production;

import java.time.Instant;
import java.util.LinkedList;

import com.bansoft.Production.model.IProduction;
import com.bansoft.Production.model.IProductionBuilder;
import com.bansoft.Production.model.ProductionJob;

public interface IProductionService {
    
    public IProductionBuilder newProduction();

    public void commitProduction(IProduction Production);
    
    public IProduction getProductionById(Long id);

    public IProduction[] getAllProductions();

	public void produce(String finalProductName,String lotNumber, String details, Instant timeInstant, LinkedList<ProductionJob> productionJobs);

    //public IEvent<IProduction> ProductionAddedEvent();
}


