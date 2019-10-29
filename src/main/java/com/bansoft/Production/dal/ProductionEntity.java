package com.bansoft.Production.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Production")
public class ProductionEntity {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    Long purchaseId;

    @Column
    Long stockId;

    @Column        
    private String productName;
    
    @Column        
    private String finalProductName;
    
    @Column        
    private Double qtyUsed;
    
    @Column        
    private Double qtyWaste;

    
    @Column        
    private Double price;
    
    @Column    
    private String lotNumber;

    @Column    
    private String details;

    @Column    
    private long timestamp;

    public ProductionEntity() {
    }
 
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getQtyUsed() {
        return qtyUsed;
    }

    public void setQtyUsed(Double qtyUsed) {
        this.qtyUsed = qtyUsed;
    }

    public Double getQtyWaste() {
        return qtyWaste;
    }

    public void setQtyWaste(Double qtyWaste) {
        this.qtyWaste = qtyWaste;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getFinalProductName() {
        return finalProductName;
    }

    public void setFinalProductName(String finalProductName) {
        this.finalProductName = finalProductName;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}