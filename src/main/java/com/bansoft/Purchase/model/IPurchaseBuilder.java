package com.bansoft.Purchase.model;

import java.time.Instant;

public interface IPurchaseBuilder{
    IPurchaseBuilder productName(String name);
    IPurchaseBuilder price(Double qty);
    IPurchaseBuilder qty(Double qty);
    IPurchaseBuilder details(String details); 
    
    IPurchaseBuilder billingId(String billingId); 

    IPurchaseBuilder timestamp(Instant dt);
    IPurchase build();
}