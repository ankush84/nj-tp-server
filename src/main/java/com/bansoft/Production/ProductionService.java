package com.bansoft.Production;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.bansoft.Production.comm.AddProductionRequest;
import com.bansoft.Production.comm.ProductionTopic;
import com.bansoft.Production.dal.ProductionDao;
import com.bansoft.Production.dal.ProductionEntity;
import com.bansoft.Production.model.IProduction;
import com.bansoft.Production.model.IProductionBuilder;
import com.bansoft.Production.model.ProductionBuilder;
import com.bansoft.Production.model.ProductionJob;
import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.model.IStock;
import com.bansoft.dal.hibernate.HibernateService;

public class ProductionService implements IProductionService {

    private HashMap<Long, IProduction> cache;
    private ProductionDao dao;
    private ProductionTopic ProductionTopic;

    public ProductionService(HibernateService hibernateService, IStockService stockService) {
        this.cache = new HashMap<>();
        this.dao = new ProductionDao(hibernateService);
        ProductionTopic = new ProductionTopic(this);
        new AddProductionRequest(this, stockService);
        this.init();
    }

    private void init() {
        List<ProductionEntity> entities = this.dao.loadAll();
        for (ProductionEntity pe : entities) {
            this.cache.put(pe.getId(), ProductionConverter.fromProductionEntityToModel(pe));
        }
    }

    @Override
    public IProductionBuilder newProduction() {
        return new ProductionBuilder();
    }

    @Override
    public void commitProduction(IProduction Production) {
        ProductionEntity pe = ProductionConverter.fromProductionModelToEntity(Production);
        dao.save(pe);
        Production.setId(pe.getId());
        cache.put(pe.getId(), Production);
        this.ProductionTopic.supplyAdd(ProductionConverter.fromProductionModelToSupply(Production));
    }

    @Override
    public IProduction getProductionById(Long id) {
        return cache.get(id);
    }

    @Override
    public IProduction[] getAllProductions() {
        return this.cache.values().toArray(new IProduction[0]);
    }

    @Override
    public void produce(String lotNumber, String details, Instant timeInstant,
            LinkedList<ProductionJob> productionJobs) {

        for (ProductionJob job : productionJobs) {

            Double totalQty = job.getQtyUsed() + job.getQtyWaste();
            Double qtyUsedPct = job.getQtyUsed() / (totalQty);
            Double qtyWastePct = job.getQtyWaste() / (totalQty);
            for (IStock stock : job.getStocks()) {

                IProductionBuilder builder = newProduction();
                builder.details(details);
                builder.lotNumber(lotNumber);
                builder.productName(job.getProductName());
                builder.stockId(stock.getId());
                builder.purchaseId(stock.getPurchaseId());
                builder.qtyUsed(stock.getQty() * qtyUsedPct);
                builder.qtyWaste(stock.getQty() * qtyWastePct);
                builder.price(stock.getPrice());
                builder.timestamp(timeInstant);
                this.commitProduction(builder.build());
            }
        }
    }
}
