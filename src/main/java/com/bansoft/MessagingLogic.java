package com.bansoft;

import com.google.gson.Gson;
import java.util.HashMap;
/**
 * MessagingLogic will be responsible for parsing received data, do 
 * authentication and forward the messages between users.
 *
 */
public class MessagingLogic {
    
    private static MessagingLogic instance;
    public static MessagingLogic getInstance() {
        if (instance == null) {
            instance = new MessagingLogic();
        }
        return instance;
    }
    
    private HashMap<String, UserSession> userSessions = new HashMap<>();
    private Gson gson = new Gson();
    
    private MessagingLogic() {
    }
    
    public void receiveText(UserSession session, String text) {
        try {
            receiveData(session, gson.fromJson(text, Data.class));
        } catch (Throwable t) {
        }
    }
    
    private void receiveData(UserSession session, Data data) {
        if (data == null) return;
        
        // for all operation except login, do session checking
        if (data.operation != Data.AUTHENTICATION_LOGIN) {
            if (!validateSession(data.session)) {
                session.disconnect(401, "Invalid session");
            }
        }
        
        switch (data.operation) {
            case Data.AUTHENTICATION_LOGIN:
                login(session, data.user);
                break;
            case Data.MESSAGING_SEND:
                send(data.message);
                break;
            default:
                session.disconnect(404, "Wrong operation");
        }
    }
    
    private void login(UserSession session, User user) {
        if (user == null) {
            session.disconnect(401, "Give username and password!");
        }
                
        if (Repository.getInstance().isValid(user)) {
            userSessions.put(user.username, session);
            
            session.setCurrentUser(user);
            
            Data data = new Data();
            data.operation = Data.AUTHENTICATION_LOGIN;
            data.session = Repository.getInstance().DUMMY_SESSION;
            
            try {
                session.receiveText(gson.toJson(data));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        } else {
            session.disconnect(401, "Wrong username or password!");
        }
    }
    
    private boolean validateSession(String session) {
        return Repository.getInstance().isValid(session);
    }
    
    private void send(Message message) {
        if (isUserOnline(message.to)) {
            System.out.println("User is online, try to send message");
            UserSession userSession = userSessions.get(message.to);
            try {
                userSession.receiveText(gson.toJson(message));
            } catch (Exception ex) {
                // put to offline message
                System.out.println("User is offline");
            }
        } else {
            // todo put to offline message
            System.out.println("User is offline");
        }
    }
    
    private boolean isUserOnline(String username) {
        return userSessions.containsKey(username);
    }
    
    public void setOffline(String username) {
        userSessions.remove(username);
        System.err.println("Set "+username+" offline.");
    }
    
}