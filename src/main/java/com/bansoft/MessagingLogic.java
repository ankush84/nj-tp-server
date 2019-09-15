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
            Data data = gson.fromJson(text, Data.class);
            if(data!=null){
            receiveData(session,data );
            }else{
            session.sendStringToRemote("text can't be parsed as valid json");                
            }
        } catch (Throwable t) {
            session.sendStringToRemote(t.getMessage());
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
            session.sendStringToRemote(gson.toJson(data));
            
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
            userSession.sendStringToRemote(gson.toJson(message));            
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