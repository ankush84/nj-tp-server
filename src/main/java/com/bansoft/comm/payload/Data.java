package com.bansoft.comm.payload;
/**
 * Data is a wrapper for transmittable object between client and server.
 *
 */
public class Data {
    public static final int VERSION = 1;    
    public static final int OPERATION_LOGIN = 1;    
    public static final int OPERATION_LOGOUT = 2;        
    public static final int OPERATION_SUBSCRIBE = 100;        
    public static final int OPERATION_SUPPLY = 200;        
    public static final int OPERATION_REQUEST = 300;        
    
    public int protocolversion = VERSION;
    public int operation;
    public User user;
    public String message;
    public String sessionId;
}