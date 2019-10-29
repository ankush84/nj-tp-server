
package com.bansoft.ProductionStock.dal;

import java.util.HashMap;
import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class ProductionStockDao {

    private HibernateService hibernateService;

    public ProductionStockDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(ProductionStockEntity entity) {
        this.hibernateService.save(entity);
    }
    public List<ProductionStockEntity> loadAll() {
        return this.hibernateService.getAll(ProductionStockEntity.class, "ProductionStockEntity");        
    }
	public List<ProductionStockEntity> loadAllAvailableProductionStocks() {
        String condition="qty > :qty"; 
        HashMap<String,Object> params=new HashMap<>();
        params.put("qty", 0.0);
        return this.hibernateService.getAllWhere(ProductionStockEntity.class, "ProductionStockEntity",condition,params);        
    }
    
    
	// public List<ProductionStockEntity> loadProductionStocksByPurchaseId(Long purchaseId) {
    //     String condition="purchaseId = :purchaseId"; 
    //     HashMap<String,Object> params=new HashMap<>();
    //     params.put("purchaseId", purchaseId);
    //     return this.hibernateService.getAllWhere(ProductionStockEntity.class, "ProductionStockEntity",condition,params);        
	// }

    public ProductionStockEntity loadById(Long id) {
        String condition = "id = :id";
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return this.hibernateService.getAllWhere(ProductionStockEntity.class, "ProductionStockEntity", condition, params).get(0);
    }
}