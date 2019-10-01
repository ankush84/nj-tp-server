package com.bansoft.Stock;

import java.util.HashMap;
import java.util.List;

import com.bansoft.Stock.comm.StockTopic;
import com.bansoft.Stock.dal.StockDao;
import com.bansoft.Stock.dal.StockEntity;
import com.bansoft.Stock.model.IStock;
import com.bansoft.Stock.model.IStockBuilder;
import com.bansoft.Stock.model.StockBuilder;
import com.bansoft.dal.hibernate.HibernateService;

public class StockService implements IStockService {

    private HashMap<Long, IStock> cache;
    private HashMap<Long, IStock> cacheByPurchaseId;
    private StockDao dao;
    private StockTopic StockTopic;

    public StockService(HibernateService hibernateService) {
        this.cache = new HashMap<>();
        this.cacheByPurchaseId= new HashMap<>();
        this.dao = new StockDao(hibernateService);
		StockTopic = new StockTopic(this);
        this.init();
    }

    private void init() {
        List<StockEntity> entities = this.dao.loadAll();
        for (StockEntity pe : entities) {
            IStock model =StockConverter.fromStockEntityToModel(pe);
            this.cache.put(pe.getId(),model);
            this.cacheByPurchaseId.put(pe.getPurchaseId(),model);
        }
    }

    @Override
    public IStockBuilder newStock() {        
        return new StockBuilder();
    }

    @Override
    public void commitStock(IStock stock) {        
        StockEntity pe =  StockConverter.fromStockModelToEntity(stock);
        dao.save(pe);        
        stock.setId(pe.getId());
        cache.put(pe.getId(), stock);
        cacheByPurchaseId.put(pe.getPurchaseId(), stock);
        this.StockTopic.supplyAdd(StockConverter.fromStockModelToSupply(stock));
    }

    @Override
    public IStock getStockById(Long id) {        
        return cache.get(id);
    }

    
    @Override
    public IStock getStockByPurchaseId(Long id) {        
        return cacheByPurchaseId.get(id);
    }

    @Override
    public IStock[] getAllStocks() {
       return this.cache.values().toArray(new IStock[0]);
    }
}

