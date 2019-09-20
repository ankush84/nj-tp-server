package com.bansoft.comm.payload;

/**
 * Message representation.
 *
 */
public class SupplyMessage {

    public static final int BATCH_BEGIN = 1;    
    public static final int BATCH_END = 2;    
    public static final int ADD = 3;    
    public static final int UPDATE = 4;    
    public static final int DELETE = 5;    

    public int phase;
    public String supply;

   //Todo : add support for limited fields subscription
}