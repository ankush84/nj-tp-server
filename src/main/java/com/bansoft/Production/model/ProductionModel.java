package com.bansoft.Production.model;

import java.time.Instant;

public class ProductionModel implements IProduction {

    private Long id;
    private Long stockId;
    private Long purchaseId;
    private String productName;
    private Double qtyUsed;
    private Double qtyWaste;
    private String details;
    private String lotNumber;
    private Instant timestamp;

    public ProductionModel(){        
    }

	@Override
	public Long getId() {		
		return this.id;
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
	public void setDetails(String details) {
		this.details=details;
		
	}

	@Override
	public void setTimestamp(Instant timestamp) {
		this.timestamp=timestamp;		
	}

	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}
    
}