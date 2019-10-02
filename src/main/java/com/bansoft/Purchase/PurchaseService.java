package com.bansoft.Purchase;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import com.bansoft.Purchase.comm.AddPurchaseRequest;
import com.bansoft.Purchase.comm.PurchaseTopic;
import com.bansoft.Purchase.dal.PurchaseDao;
import com.bansoft.Purchase.dal.PurchaseEntity;
import com.bansoft.Purchase.model.IPurchase;
import com.bansoft.Purchase.model.IPurchaseBuilder;
import com.bansoft.Purchase.model.PurchaseBuilder;
import com.bansoft.Stock.IStockService;
import com.bansoft.Stock.model.IStock;
import com.bansoft.dal.hibernate.HibernateService;

public class PurchaseService implements IPurchaseService {

    private HashMap<Long, IPurchase> cache;
    private PurchaseDao dao;
    private PurchaseTopic purchaseTopic;
    private IStockService stockService;

    public PurchaseService(HibernateService hibernateService, IStockService stockService) {
        this.stockService = stockService;
        this.cache = new HashMap<>();
        this.dao = new PurchaseDao(hibernateService);
        purchaseTopic = new PurchaseTopic(this);
        new AddPurchaseRequest(this);
        this.init();
    }

    private void init() {
        List<PurchaseEntity> entities = this.dao.loadAll();
        for (PurchaseEntity pe : entities) {
            this.cache.put(pe.getId(), PurchaseConverter.fromPurchaseEntityToModel(pe));
        }
    }

    @Override
    public IPurchaseBuilder newPurchase() {
        return new PurchaseBuilder();
    }

    @Override
    public void commitPurchase(IPurchase purchase) {
        PurchaseEntity pe = PurchaseConverter.fromPurchaseModelToEntity(purchase);
        dao.save(pe);
        purchase.setId(pe.getId());
        if (cache.containsKey(pe.getId())) {
            // old purchase. Update stock
            //IStock stock = stockService.getStockByPurchaseId(pe.getId());
            // stock.set
        } else {        
            IStock stock = stockService.newStock().productName(pe.getProductName())
            .purchaseId(pe.getId()).qty(pe.getQty()).timestamp(Instant.now())
                    .build();
                stockService.commitStock(stock);
        }
        cache.put(pe.getId(), purchase);
        this.purchaseTopic.supplyAdd(PurchaseConverter.fromPurchaseModelToSupply(purchase));
    }

    @Override
    public IPurchase getPurchaseById(Long id) {
        return cache.get(id);
    }

    @Override
    public IPurchase[] getAllPurchases() {
        return this.cache.values().toArray(new IPurchase[0]);
    }
}
