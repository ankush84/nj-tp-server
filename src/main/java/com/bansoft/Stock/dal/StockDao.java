
package com.bansoft.Stock.dal;

import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class StockDao {

    private HibernateService hibernateService;

    public StockDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(StockEntity student) {
        this.hibernateService.save(student);
    }
    public List<StockEntity> loadAll() {
        return this.hibernateService.getAll(StockEntity.class, "StockEntity");        
    }
}