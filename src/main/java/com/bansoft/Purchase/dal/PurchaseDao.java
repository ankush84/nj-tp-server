
package com.bansoft.Purchase.dal;

import java.util.List;

import com.bansoft.dal.hibernate.HibernateService;

public class PurchaseDao {

    private HibernateService hibernateService;

    public PurchaseDao(HibernateService hibernateService) {
        super();
        this.hibernateService = hibernateService;
    }
    public void save(PurchaseEntity student) {
        this.hibernateService.save(student);
    }
    public List<PurchaseEntity> loadAll() {
        return this.hibernateService.getAll(PurchaseEntity.class, "PurchaseEntity");        
    }
}