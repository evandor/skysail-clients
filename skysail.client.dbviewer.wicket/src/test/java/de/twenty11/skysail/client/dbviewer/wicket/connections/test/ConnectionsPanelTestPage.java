package de.twenty11.skysail.client.dbviewer.wicket.connections.test;

import org.apache.wicket.markup.html.WebPage;

import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsPanel;

public class ConnectionsPanelTestPage extends WebPage {

    private static final long serialVersionUID = -7532754691242403980L;

    public ConnectionsPanelTestPage() {
        add(new ConnectionsPanel("connectionPanel"));
    }

}
