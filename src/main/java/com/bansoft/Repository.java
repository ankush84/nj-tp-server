package com.bansoft;
import java.util.HashMap;
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
        User user = new User("alice", "123456");
        users.put(user.username, user);
        user = new User("bob", "654321");
        users.put(user.username, user);
        user = new User("charlie", "qwerty");
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