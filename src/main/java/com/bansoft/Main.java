package com.bansoft;

import com.bansoft.Purchase.PurchaseTopic;
import com.bansoft.dal.hibernate.HibernateService;

import org.hibernate.Hibernate;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		MessagingServer theServer = new MessagingServer(9080);
		theServer.setup();
		
		HibernateService hibernateService = new HibernateService();
		PurchaseTopic purchaseTopic = new PurchaseTopic(hibernateService);
        theServer.start();
	}
}
