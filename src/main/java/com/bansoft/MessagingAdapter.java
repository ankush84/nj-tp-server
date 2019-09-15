//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;
/**
 * MessagingAdapter responsible to handle connection, receiving data, forward 
 * the data to @see com.bansoft.MessagingLogic and receive 
 * data from @see com.bansoft.MessagingLogic to be 
 * delivered to recipient.
 * 
 */
public class MessagingAdapter extends WebSocketAdapter implements UserSession {
    
    private User currentUser;
    
    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        MessagingLogic.getInstance().setOffline(currentUser.username);        
        System.err.println("Close connection "+statusCode+", "+reason);        
        super.onWebSocketClose(statusCode, reason); 
    }

    @Override
    public void onWebSocketText(String message) {
        super.onWebSocketText(message); 
        
        MessagingLogic.getInstance().receiveText(this, message);
    }
    
    @Override
    public void sendStringToRemote(String text) {
        if (getSession() != null && getSession().isOpen()) {
           try {
            
            getSession().getRemote().sendString(text);

            } catch (Exception ex) {
                // put to offline message
                System.out.println("User is offline");
            }
        }
    }
   
    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
    @Override
    public void disconnect(int status, String reason) {        
        getSession().close(status, reason);        
    }
    
}