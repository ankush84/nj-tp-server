
package com.bansoft.Stock.dal;

import java.util.HashMap;
import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class StockDao {

    private HibernateService hibernateService;

    public StockDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(StockEntity entity) {
        this.hibernateService.save(entity);
    }
    public List<StockEntity> loadAll() {
        return this.hibernateService.getAll(StockEntity.class, "StockEntity");        
    }
	public List<StockEntity> loadAllAvailableStocks() {
        String condition="qty > :qty"; 
        HashMap<String,Object> params=new HashMap<>();
        params.put("qty", 0.0);
        return this.hibernateService.getAllWhere(StockEntity.class, "StockEntity",condition,params);        
    }
    
    
	public List<StockEntity> loadStocksByPurchaseId(Long purchaseId) {
        String condition="purchaseId = :purchaseId"; 
        HashMap<String,Object> params=new HashMap<>();
        params.put("purchaseId", purchaseId);
        return this.hibernateService.getAllWhere(StockEntity.class, "StockEntity",condition,params);        
	}

    public StockEntity loadById(Long id) {
        String condition = "id = :id";
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return this.hibernateService.getAllWhere(StockEntity.class, "StockEntity", condition, params).get(0);
    }
}