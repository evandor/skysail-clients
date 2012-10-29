package de.twenty11.skysail.client.dbviewer.wicket;

import java.util.Locale;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class DbViewerSession extends WebSession {

    private static final long serialVersionUID = -4830435111341816343L;

    private User user;
    private String activeConnection;

    public static DbViewerSession get() {
        return (DbViewerSession) Session.get();
    }

    public DbViewerSession(Request request) {
        super(request);
        setLocale(Locale.ENGLISH);
    }

    public synchronized User getUser() {
        return user;
    }

    public synchronized boolean isAuthenticated() {
        return (user != null);
    }

    public synchronized void setUser(User user) {
        this.user = user;
        dirty();
    }

    // TODO make sure only autorized users connections can end up here
    public void setActiveConnection(String id) {
        this.activeConnection = id;
    }
    
    public String getActiveConnection() {
        return activeConnection;
    }
    
}
