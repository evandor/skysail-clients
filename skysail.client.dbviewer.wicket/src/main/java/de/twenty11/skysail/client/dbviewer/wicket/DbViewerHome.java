package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class DbViewerHome extends WebPage { // extends DbViewerTemplate {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerHome() {
        add(new Label("oneComponent", "Welcome7"));
        // add(new ConnectionPanel("connectionPanel"));
    }
}
