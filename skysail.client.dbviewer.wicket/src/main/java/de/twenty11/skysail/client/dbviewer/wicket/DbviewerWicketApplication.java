package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.protocol.http.WebApplication;

public class DbviewerWicketApplication extends WebApplication {

    @Override
    public Class<DbViewerTemplate> getHomePage() {
        return DbViewerTemplate.class;
    }

}
