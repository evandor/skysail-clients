package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.markup.html.basic.Label;

public class DbViewerHome extends DbViewerTemplate {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerHome() {
        add(new Label("oneComponent", "Welcome!!!!"));
        add(new ConnectionPanel("connectionPanel"));
    }
}
