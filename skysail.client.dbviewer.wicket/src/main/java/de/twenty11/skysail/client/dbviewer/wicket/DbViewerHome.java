package de.twenty11.skysail.client.dbviewer.wicket;

import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsPanel;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.TablesProxy;
import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesPanel;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;

public class DbViewerHome extends DbViewerTemplate {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerHome() {
        add(new ConnectionsPanel("connectionsPanel", new ConnectionsProxy()));
        add(new TablesPanel("tablesPanel", new TablesProxy()));
    }

}
