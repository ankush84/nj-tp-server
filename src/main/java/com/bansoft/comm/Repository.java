package com.bansoft.comm;

import java.util.HashMap;

import com.bansoft.comm.payload.User;
/**
 * Repository responsible for all operation related to data saving or retrieval.
 *
 */
public class Repository {
    private static Repository instance;
    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }
    
    private final HashMap<String, User> users = new HashMap<>();
    public final String DUMMY_SESSION = "SOMEVALIDSESSION";
    
    private Repository() {
        initDummyUser();
    }
    
    private void initDummyUser() {
        User user = new User("nikhil", "nikhil");
        users.put(user.username, user);
        user = new User("anshul", "anshul");
        users.put(user.username, user);
        user = new User("naveen", "naveen");
        users.put(user.username, user);
    }
    
    public boolean isValid(User user) {
        User item = users.get(user.username);
        if (item != null && item.password.equals(user.password)) {
            return true;
        }
        
        return false;
    }
    
    public boolean isValid(String session) {
        if (DUMMY_SESSION.equals(session)) {
            return true;
        }
        return false;
    }
}