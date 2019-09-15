package com.bansoft;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		 MessagingServer theServer = new MessagingServer();
        theServer.setup();
        theServer.start();
	}
}
