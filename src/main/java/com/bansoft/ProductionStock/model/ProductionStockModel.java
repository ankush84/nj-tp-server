package com.bansoft.ProductionStock.model;

import java.time.Instant;

public class ProductionStockModel implements IProductionStock {

    private Long id;
    private String lotNumber;
    private String productName;
    private Double qty;
    private Double cost;
    private Instant timestamp;

    public ProductionStockModel(){        
    }

	@Override
	public Long getId() {		
		return this.id;
	}

	@Override
	public String getLotNumber() {		
		return this.lotNumber;
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
    public void setLotNumber(String id) {		
        this.lotNumber = id;
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

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}
    
}