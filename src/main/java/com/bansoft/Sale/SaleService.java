package com.bansoft.Sale;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import com.bansoft.Sale.comm.AddSaleRequest;
import com.bansoft.Sale.comm.SaleTopic;
import com.bansoft.Sale.dal.SaleDao;
import com.bansoft.Sale.dal.SaleEntity;
import com.bansoft.Sale.model.ISale;
import com.bansoft.Sale.model.ISaleBuilder;
import com.bansoft.Sale.model.SaleBuilder;
import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.model.IStock;
import com.bansoft.dal.hibernate.HibernateService;

public class SaleService implements ISaleService {

    private HashMap<Long, ISale> cache;
    private SaleDao dao;
    private SaleTopic SaleTopic;
    private IStockService stockService;

    public SaleService(HibernateService hibernateService, IStockService stockService) {
        this.stockService = stockService;
        this.cache = new HashMap<>();
        this.dao = new SaleDao(hibernateService);
        SaleTopic = new SaleTopic(this);
        new AddSaleRequest(this);
        this.init();
    }

    private void init() {
        List<SaleEntity> entities = this.dao.loadAll();
        for (SaleEntity pe : entities) {
            this.cache.put(pe.getId(), SaleConverter.fromSaleEntityToModel(pe));
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
        if (cache.containsKey(pe.getId())) {
            // old Sale. Update stock
            IStock stock = stockService.getStockBySaleId(pe.getId());
            // stock.set
        } else {        

            IStock stock = stockService.newStock()
            .SaleId(pe.getId()).qty(pe.getQty()).timestamp(Instant.now())
                    .build();
                stockService.commitStock(stock);
        }
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
}
