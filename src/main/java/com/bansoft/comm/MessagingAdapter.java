//https://itnext.io/writing-a-web-socket-server-with-embedded-jetty-46fe9ab1c435
package com.bansoft.comm;

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

    private Data data;
    private Gson gson = new Gson();

    @Override
    public void onWebSocketText(String text) {
        super.onWebSocketText(text);

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
                    SubscriptionService.getInstance().addSubscription(sm.topic, this);
                    break;
                case Data.OPERATION_REQUEST:
                if (!isValidSession(data.sessionId))
                    break;
                RequestMessage rm = gson.fromJson(data.message, RequestMessage.class);
                RequestService.getInstance().getReply(this, rm);
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
        logOut(data);
        System.err.println("Close connection " + statusCode + ", " + reason);
        super.onWebSocketClose(statusCode, reason);
    }

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

    public String getSessionId() {
        return data.sessionId;
    }

    public Gson getGson() {
        return gson;
    }

    private void login(Data data) {
        if (data.user == null) {
            getSession().close(401, "Give username and password!");
        }

        if (Repository.getInstance().isValid(data.user)) {
            String sessionId = Sessions.getInstance().add(this);
            this.data = data;

            Data replyData = new Data();
            replyData.operation = Data.OPERATION_LOGIN_RESULT;
            replyData.user = data.user;
            replyData.sessionId = sessionId;
            sendStringToRemote(gson.toJson(replyData));
        } else {
            getSession().close(401, "Wrong username or password!");
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
        return data.sessionId.equals(sessionId) && Sessions.getInstance().isValid(sessionId);
    }
}