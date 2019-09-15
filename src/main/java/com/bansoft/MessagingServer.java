package com.bansoft;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class MessagingServer {
    
    private Server server;
    
    public void setup() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(9080);
        server.addConnector(connector);
        
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        server.setHandler(handler);
        
        handler.addServlet(MessagingServlet.class, "/messaging");
    }
    
    public void start() throws Exception {
        server.start();
        server.dump(System.err);
        server.join();
    }
    
    
}