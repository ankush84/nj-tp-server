package com.bansoft.comm;
import java.util.HashMap;
import java.util.UUID;

import antlr.collections.List;
/**
 * Repository responsible for all operation related to data saving or retrieval.
 *
 */
public class Sessions {
    
    private static class BillPughSingleton {
        private static final Sessions INSTANCE = new Sessions();
    }

    public static Sessions getInstance() {
        return BillPughSingleton.INSTANCE;
    }   
    
    private final HashMap<String, MessagingAdapter> cache;
    //public final String DUMMY_SESSION = "SOMEVALIDSESSION";
    
    private Sessions() {
        cache = new HashMap<>();
    }

	public String add(MessagingAdapter messagingAdapter) {
        String sessionId = UUID.randomUUID().toString();
        this.cache.put(sessionId, messagingAdapter);
        return sessionId;
    }
    
    public MessagingAdapter getByUserName(String username) {
        return this.cache.get(username);
    }
    
    public MessagingAdapter[] getAll(String username, MessagingAdapter messagingAdapter) {
        return this.cache.values().toArray(new MessagingAdapter[0]);
	}

	public boolean isValid(String sessionId) {
		return this.cache.containsKey(sessionId);
	}

	public void remove(String sessionId) {
        if(isValid(sessionId)){
            this.cache.remove(sessionId);
        }
	}
}