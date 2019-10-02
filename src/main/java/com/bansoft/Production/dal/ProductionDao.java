
package com.bansoft.Production.dal;

import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class ProductionDao {

    private HibernateService hibernateService;

    public ProductionDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(ProductionEntity student) {
        this.hibernateService.save(student);
    }
    public List<ProductionEntity> loadAll() {
        return this.hibernateService.getAll(ProductionEntity.class, "ProductionEntity");        
    }
}