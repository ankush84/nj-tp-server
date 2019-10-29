package com.bansoft;

import com.bansoft.Production.ProductionService;
import com.bansoft.ProductionStock.ProductionStockService;
import com.bansoft.Purchase.PurchaseService;
import com.bansoft.Sale.SaleService;
import com.bansoft.Stock.StockService;
import com.bansoft.dal.hibernate.HibernateService;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		MessagingServer theServer = new MessagingServer(9080);
		theServer.setup();

		HibernateService hibernateService = new HibernateService();
		StockService stockService = new StockService(hibernateService);
		ProductionStockService pss=new ProductionStockService(hibernateService);
		new PurchaseService(hibernateService, stockService);
		new ProductionService(hibernateService, stockService,pss);
		new SaleService(hibernateService, pss);

		theServer.start();
	}
}
