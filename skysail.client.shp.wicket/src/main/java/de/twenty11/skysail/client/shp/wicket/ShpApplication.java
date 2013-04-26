package de.twenty11.skysail.client.shp.wicket;

import org.apache.wicket.Application;
import org.apache.wicket.protocol.http.WebApplication;

public class ShpApplication extends WebApplication {

    @Override
    public Class<ShpHome> getHomePage() {
        return ShpHome.class;
    }

    @Override
    protected void init() {
        super.init();
        mountPage("/impressum", ImpressumPage.class);
        mountPage("/contact", ContactPage.class);
    }

    public static ShpApplication get() {
        return (ShpApplication) Application.get();
    }

    // @Override
    // public Session newSession(Request request, Response response) {
    // return new DbViewerSession(request);
    // }

}
