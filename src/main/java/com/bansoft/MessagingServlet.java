package com.bansoft;

import com.bansoft.comm.MessagingAdapter;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class MessagingServlet extends WebSocketServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(MessagingAdapter.class);
    }
    
}