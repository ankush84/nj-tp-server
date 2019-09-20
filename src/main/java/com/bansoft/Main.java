package com.bansoft;

import com.bansoft.Purchase.PurchaseService;
import com.bansoft.dal.hibernate.HibernateService;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		MessagingServer theServer = new MessagingServer(9080);
		theServer.setup();
		
		HibernateService hibernateService = new HibernateService();
        new PurchaseService(hibernateService);

        theServer.start();
	}
}
