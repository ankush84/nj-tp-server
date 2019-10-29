package com.bansoft.Sale;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.bansoft.ProductionStock.IProductionStockService;
import com.bansoft.ProductionStock.model.IProductionStock;
import com.bansoft.Sale.comm.AddSaleRequest;
import com.bansoft.Sale.comm.SaleTopic;
import com.bansoft.Sale.dal.SaleDao;
import com.bansoft.Sale.dal.SaleEntity;
import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.ISaleBuilder;
import com.bansoft.Sale.model.SaleBuilder;
import com.bansoft.Sale.model.SaleJob;
import com.bansoft.dal.hibernate.HibernateService;

public class SaleService implements ISaleService {

    private HashMap<Long, ISale> cache;
    private SaleDao dao;
    private SaleTopic SaleTopic;
    private long maxSaleNumber=0;

    public SaleService(HibernateService hibernateService, IProductionStockService stockService) {
        this.cache = new HashMap<>();
        this.dao = new SaleDao(hibernateService);
        SaleTopic = new SaleTopic(this);
        new AddSaleRequest(this, stockService);
        this.init();
    }

    private void init() {
        List<SaleEntity> entities = this.dao.loadAll();
        for (SaleEntity pe : entities) {
            this.cache.put(pe.getId(), SaleConverter.fromSaleEntityToModel(pe));

            Integer lotNumber =Integer.parseInt(pe.getLotNumber());
            if(maxSaleNumber<lotNumber){
                maxSaleNumber=lotNumber;
            }
        }
    }

    @Override
    public ISaleBuilder newSale() {
        return new SaleBuilder();
    }

    @Override
    public void commitSale(ISale Sale) {
        SaleEntity pe = SaleConverter.fromSaleModelToEntity(Sale);
        
        dao.save(pe);
        Sale.setId(pe.getId());
        cache.put(pe.getId(), Sale);
        this.SaleTopic.supplyAdd(SaleConverter.fromSaleModelToSupply(Sale));
    }

    @Override
    public ISale getSaleById(Long id) {
        return cache.get(id);
    }

    @Override
    public ISale[] getAllSales() {
        return this.cache.values().toArray(new ISale[0]);
    }

    @Override
    public void produce(String party, String billNumber, String details, Instant timeInstant,
            LinkedList<SaleJob> SaleJobs) {
               
        Long saleNumber =++maxSaleNumber;
                
        for (SaleJob job : SaleJobs) {

            Double totalQty = job.getQtyUsed();
            Double qtyUsedPct = job.getQtyUsed() / (totalQty);
            for (IProductionStock stock : job.getStocks()) {

                ISaleBuilder builder = newSale();
                builder.party(party);
                builder.billNo(billNumber);
                builder.saleNumber(saleNumber);
                builder.details(details);
                builder.productName(job.getProductName());
                builder.lotNumber(stock.getLotNumber());
                builder.qtyUsed(stock.getQty() * qtyUsedPct);
                builder.timestamp(timeInstant);
                this.commitSale(builder.build());
            }
        }
    }
}
