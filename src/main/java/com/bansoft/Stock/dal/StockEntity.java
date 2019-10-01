package com.bansoft.Stock.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Stock")
public class StockEntity {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    Long purchaseId;
        
    @Column        
    private Double qty;
    
    @Column    
    private long timestamp;

    public StockEntity() {
    }
 
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    
    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

}