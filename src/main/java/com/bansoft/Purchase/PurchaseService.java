package com.bansoft.Purchase;

import java.util.HashMap;
import java.util.List;

import com.bansoft.Purchase.comm.AddPurchaseRequest;
import com.bansoft.Purchase.comm.PurchaseTopic;
import com.bansoft.Purchase.dal.PurchaseDao;
import com.bansoft.Purchase.dal.PurchaseEntity;
import com.bansoft.Purchase.model.IPurchase;
import com.bansoft.Purchase.model.IPurchaseBuilder;
import com.bansoft.Purchase.model.PurchaseBuilder;
import com.bansoft.dal.hibernate.HibernateService;

public class PurchaseService implements IPurchaseService {

    private HashMap<Long, IPurchase> cache;
    private PurchaseDao dao;
    private PurchaseTopic purchaseTopic;

    public PurchaseService(HibernateService hibernateService) {
        this.cache = new HashMap<>();
        this.dao = new PurchaseDao(hibernateService);
		purchaseTopic = new PurchaseTopic(this);
        new AddPurchaseRequest(this);
        this.init();
    }

    private void init() {
        List<PurchaseEntity> entities = this.dao.loadAll();
        for (PurchaseEntity pe : entities) {
            this.cache.put(pe.getId(),PurchaseConverter.fromPurchaseEntityToModel(pe));
        }
    }

    @Override
    public IPurchaseBuilder newPurchase() {        
        return new PurchaseBuilder();
    }

    @Override
    public void commitPurchase(IPurchase purchase) {        
        PurchaseEntity pe =  PurchaseConverter.fromPurchaseModelToEntity(purchase);
        dao.save(pe);        
        purchase.setId(pe.getId());
        cache.put(pe.getId(), purchase);
        this.purchaseTopic.supplyAdd(purchase);
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

