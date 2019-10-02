package com.bansoft.Stock.model;

import java.time.Instant;

public class StockModel implements IStock {

    private Long id;
    private Long purchaseId;
    private String productName;
    private Double qty;
    private Instant timestamp;

    public StockModel(){        
    }

	@Override
	public Long getId() {		
		return this.id;
	}

	@Override
	public Long getPurchaseId() {		
		return this.purchaseId;
	}

	@Override
	public Double getQty() {		
		return this.qty;
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
    public void setPurchaseId(Long id) {		
        this.purchaseId = id;
    }


	@Override
	public void setQty(Double qty) {
		this.qty=qty;	
	}


	@Override
	public void setTimestamp(Instant timestamp) {
		this.timestamp=timestamp;		
	}

	@Override
	public String getProductName() {
		return productName;
	}

	@Override
	public void setProductName(String productName) {
		this.productName = productName;
	}
    
}