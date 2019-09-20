package com.bansoft.Purchase;

import java.util.HashMap;
import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;
import com.bansoft.dal.hibernate.dao.PurchaseDao;
import com.bansoft.dal.hibernate.entities.PurchaseEntity;

public class PurchaseService implements IPurchaseService {

    private HashMap<Long, IPurchase> cache;
    private PurchaseDao dao;

    public PurchaseService(HibernateService hibernateService) {
        this.cache = new HashMap<>();
        this.dao = new PurchaseDao(hibernateService);
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
        //todo: raise event
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

