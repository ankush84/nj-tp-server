package com.bansoft.Stock.model;

import java.time.Instant;

public class StockModel implements IStock {

    private Long id;
    private Long purchaseId;
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
		return this.id;
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
        this.id = id;
    }


	@Override
	public void setQty(Double qty) {
		this.qty=qty;	
	}


	@Override
	public void setTimestamp(Instant timestamp) {
		this.timestamp=timestamp;		
	}
    
}