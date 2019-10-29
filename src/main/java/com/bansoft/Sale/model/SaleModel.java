package com.bansoft.Sale.model;

import java.time.Instant;

public class SaleModel implements ISale {

    private Long id;
    private String party;
    private String billNo;
    private Long saleNo;
    private String productName;
    private Double qtyUsed;
    private String details;
    private String lotNumber;
    private Instant timestamp;

    public SaleModel(){        
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