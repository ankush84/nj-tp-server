package com.bansoft;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class MessagingServer {
    
    private Server server;
    private int port;
    public MessagingServer(int port) {
     this.port=port;   
    }
    public void setup() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);
        
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        handler.addServlet(MessagingServlet.class, "/messaging");


        // final String extForm = this.getClass().getClassLoader().getResource("webapp").toExternalForm();
        // final ResourceHandler resHandler = new ResourceHandler();
        // resHandler.setResourceBase(extForm);
        // final ContextHandler ctx = new ContextHandler("/");
        // ctx.setHandler(resHandler);


        final String webAppPath =Paths.get(System.getProperty("user.dir"),"src","main","resources","webapp").toAbsolutePath().toString();
        final ResourceHandler webAppResHandler = new ResourceHandler();
        webAppResHandler.setResourceBase(webAppPath);
        final ContextHandler webAppCtx = new ContextHandler("/client");
        webAppCtx.setHandler(webAppResHandler);

        


        server.insertHandler(handler);
        server.insertHandler(webAppResHandler);
        
    }
    
    public void start() throws Exception {
        server.start();
        server.dump(System.err);
        server.join();
    }
    
    
}