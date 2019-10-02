
package com.bansoft.Sale.dal;

import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class SaleDao {

    private HibernateService hibernateService;

    public SaleDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(SaleEntity student) {
        this.hibernateService.save(student);
    }
    public List<SaleEntity> loadAll() {
        return this.hibernateService.getAll(SaleEntity.class, "SaleEntity");        
    }
}