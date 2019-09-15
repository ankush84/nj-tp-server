package com.bansoft;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
/**
 * MessagingAdapter responsible to handle connection, receiving data, forward 
 * the data to @see com.bansoft.MessagingLogic and receive 
 * data from @see com.bansoft.MessagingLogic to be 
 * delivered to recipient.
 * 
 */
public class MessagingAdapter extends WebSocketAdapter implements UserSession {
    
    private Session session;
    private User currentUser;
    
    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session); 
        
        this.session = session;
    }
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        MessagingLogic.getInstance().setOffline(currentUser.username);
        
        this.session = null;
        
        System.err.println("Close connection "+statusCode+", "+reason);
        
        super.onWebSocketClose(statusCode, reason); 
    }
    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message); 
        
        MessagingLogic.getInstance().receiveText(this, message);
    }
    @Override
    public void receiveText(String text) throws Exception {
        if (session != null && session.isOpen()) {
            session.getRemote().sendString(text);
        }
    }
    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    @Override
    public void disconnect(int status, String reason) {
        
        session.close(status, reason);
        disconnect(status, reason);
    }
    
}