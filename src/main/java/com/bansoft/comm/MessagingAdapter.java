//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

import java.time.Instant;

import com.bansoft.comm.payload.Data;
import com.bansoft.comm.payload.RequestMessage;
import com.bansoft.comm.payload.SubscriptionMessage;
import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;

/**
 * MessagingAdapter responsible to handle connection, receiving data, forward
 * the data to @see com.bansoft.aaaa and receive data from @see com.bansoft.aaaa
 * to be delivered to recipient.
 * 
 */
public class MessagingAdapter extends WebSocketAdapter {

    private Data loginData;
    private Gson gson = new Gson();

    @Override
    public void onWebSocketText(String text) {
        super.onWebSocketText(text);
        System.out.println(Instant.now().toString()+ "- remoteIn {" + text + "}");
        try {
            Data data = gson.fromJson(text, Data.class);
            if (data != null) {

                switch (data.operation) {
                case Data.OPERATION_LOGIN:
                    login(data);
                    break;
                case Data.OPERATION_LOGOUT:
                    logOut(data);
                    break;
                case Data.OPERATION_SUBSCRIBE:
                    if (!isValidSession(data.sessionId))
                        break;

                    SubscriptionMessage sm = gson.fromJson(data.message, SubscriptionMessage.class);
                    SubscriptionService.getInstance().addSubscription(sm.id, sm.topic, this);
                    break;
                case Data.OPERATION_UNSUBSCRIBE:
                    if (!isValidSession(data.sessionId))
                        break;

                    SubscriptionMessage unsm = gson.fromJson(data.message, SubscriptionMessage.class);
                    SubscriptionService.getInstance().removeSubscription(unsm.id, unsm.topic, this);
                    break;
                case Data.OPERATION_REQUEST:
                    if (!isValidSession(data.sessionId))
                        break;
                    RequestMessage rm = gson.fromJson(data.message, RequestMessage.class);
                    RequestService.getInstance().getReply(this, rm);
                    break;
                case Data.OPERATION_PING:
                    if (!isValidSession(data.sessionId))
                        break;
            
                    data.operation=Data.OPERATION_PONG;
                    sendStringToRemote(gson.toJson(data));
                    
                    break;

                default:
                    getSession().close(404, "Wrong operation");
                }
            }
        } catch (Throwable t) {
            sendStringToRemote(t.getMessage());
        }
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        logOut(loginData);
        System.err.println("Close connection " + statusCode + ", " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

    public void sendStringToRemote(String text) {
        System.out.println(Instant.now().toString()+ "- remoteOut {" + text + "}");

        if (getSession() != null && getSession().isOpen()) {
            try {

                getSession().getRemote().sendString(text);

            } catch (Exception ex) {
                // put to offline message
                System.out.println("Ex while sending remoteOut: " + ex.toString());
            }
        }else{
            System.out.println("remoteOut not send userseems offline");
        }
    }

    public Gson getGson() {
        return gson;
    }

    private void login(Data data) {
        if (data.user == null) {
            getSession().close(401, "Give username and password!");
        }

        if (Repository.getInstance().isValid(data.user)) {
            this.loginData = data;
            this.loginData.sessionId = Sessions.getInstance().add(this);

            Data replyData = new Data();
            replyData.operation = Data.OPERATION_LOGIN_RESULT;
            replyData.user = data.user;
            replyData.sessionId = this.loginData.sessionId;
            sendStringToRemote(gson.toJson(replyData));
        } else {
            Data replyData = new Data();
            replyData.operation = Data.OPERATION_LOGIN_RESULT;
            replyData.user = data.user;
            replyData.message = "Login failed";
            sendStringToRemote(gson.toJson(replyData));

        }
    }

    private void logOut(Data data) {
        if (data.sessionId == null)
            return;
        if (!Sessions.getInstance().isValid(data.sessionId))
            return;

        Sessions.getInstance().remove(data.sessionId);
    }

    private boolean isValidSession(String sessionId) {
        return loginData.sessionId.equals(sessionId) && Sessions.getInstance().isValid(sessionId);
    }

    public String getSessionId() {
        if (loginData == null)
            return null;

        return this.loginData.sessionId;
    }
}