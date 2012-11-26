package skysail.client.dbviewer.e4;

import org.restlet.Request;

public class DbViewerSession  {

    private static final long serialVersionUID = -4830435111341816343L;

    private static DbViewerSession self;

    private transient User user;

    public static DbViewerSession get() {
        return self;
    }

    public DbViewerSession(Request request) {
        self = this;
    }

    public synchronized User getUser() {
        return user;
    }

    public synchronized boolean isAuthenticated() {
        return (user != null);
    }

    public synchronized void setUser(User user) {
        this.user = user;
    }


}
