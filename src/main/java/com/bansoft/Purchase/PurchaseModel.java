package com.bansoft.Purchase;

import java.time.Instant;

public class PurchaseModel implements IPurchase {

    private Long id;
    private String productName;
    private Double price;
    private Double qty;
    private String details;
    private String billingId;
    private Instant timestamp;

    public PurchaseModel(){        
    }

	@Override
	public Long getId() {		
		return this.id;
	}

	@Override
	public String getProductName() {		
		return this.productName;
	}

	@Override
	public Double getPrice() {		
		return this.price;
	}

	@Override
	public Double getQty() {		
		return this.qty;
	}

	@Override
	public String getBillingId() {		
		return this.billingId;
	}

	@Override
	public String getDetails() {		
		return this.details;
	}

	@Override
	public Instant getTimestamp() {		
		return this.timestamp;
	}

    @Override
    public void setId(Long id) {		
        this.id = id;
    }


	@Override
	public void setProductName(String productName) {
		this.productName= productName;		
	}

	@Override
	public void setPrice(Double price) {
		this.price=price;		
	}

	@Override
	public void setQty(Double qty) {
		this.qty=qty;	
	}

	@Override
	public void setBillingId(String billingId) {
		this.billingId=billingId;		
	}

	@Override
	public void setDetails(String details) {
		this.details=details;
		
	}

	@Override
	public void setTimestamp(Instant timestamp) {
		this.timestamp=timestamp;		
	}
    
}