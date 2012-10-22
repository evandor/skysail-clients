package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class DbviewerWicketApplication extends WebApplication {

    @Override
    public Class<DbViewerHome> getHomePage() {
        return DbViewerHome.class;
    }

    @Override
    protected void init() {
        super.init();
    }

    public static DbviewerWicketApplication get() {
        return (DbviewerWicketApplication) Application.get();
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new DbViewerSession(request);
    }

}
