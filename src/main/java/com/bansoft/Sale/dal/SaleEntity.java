package com.bansoft.Sale.dal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Sale")
public class SaleEntity {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column        
    private String productName;
    
    @Column        
    private String party;
    
    @Column        
    private String billNo;
    
    @Column        
    private Long saleNo;
    
    @Column        
    private Double qtyUsed;
    
    @Column    
    private String lotNumber;

    @Column    
    private String details;

    @Column    
    private long timestamp;

    public SaleEntity() {
    }
 
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
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

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public Long getSaleNo() {
        return saleNo;
    }

    public void setSaleNo(Long saleNo) {
        this.saleNo = saleNo;
    }

}