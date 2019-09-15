package com.bansoft;
//import id.amrishodiq.jettywebsocket.data.User;
/**
 * UserSession is an interface to track every client connection.
 *
 */
public interface UserSession {
    void receiveText(String text) throws Exception;
    void setCurrentUser(User user);
    void disconnect(int status, String reason);
}