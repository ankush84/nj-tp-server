package com.bansoft.Sale.model;

import java.time.Instant;

public interface ISaleBuilder{
    ISaleBuilder productName(String name);
    ISaleBuilder price(Double qty);
    ISaleBuilder qty(Double qty);
    ISaleBuilder details(String details); 
    
    ISaleBuilder billingId(String billingId); 

    ISaleBuilder timestamp(Instant dt);
    ISale build();
}