package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.Session;
import org.apache.wicket.request.ClientInfo;
import org.apache.wicket.request.Request;

public class DbviewerSession extends Session {

    private static final long serialVersionUID = -6979590539292358715L;

    public DbviewerSession(Request request) {
        super(request);
    }

    @Override
    public void cleanupFeedbackMessages() {
        // TODO Auto-generated method stub

    }

    @Override
    public ClientInfo getClientInfo() {
        // TODO Auto-generated method stub
        return null;
    }

}
