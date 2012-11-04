package de.twenty11.skysail.client.dbviewer.wicket;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;

import de.twenty11.skysail.client.dbviewer.wicket.columns.ColumnsPanel;
import de.twenty11.skysail.client.dbviewer.wicket.columns.ColumnsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsPanel;
import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.data.DataPanel;
import de.twenty11.skysail.client.dbviewer.wicket.schemas.SchemasPanel;
import de.twenty11.skysail.client.dbviewer.wicket.schemas.SchemasProxy;
import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesPanel;
import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesProxy;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;

public class DbViewerHome extends DbViewerTemplate {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerHome() {
        add(new ConnectionsPanel("connectionsPanel", new ConnectionsProxy()));
        add(new SchemasPanel("schemesPanel", new SchemasProxy()));
        add(new TablesPanel("tablesPanel", new TablesProxy()));

        add(new BookmarkablePageLink<String>("addConnection", ConnectionPage.class));

        List tabs = new ArrayList();

        AbstractTab dataPanelTab = new AbstractTab(new Model<String>("dataPanel")) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new DataPanel(panelId, new ColumnsProxy());
            }
        };

        AbstractTab columnsPanelTab = new AbstractTab(new Model<String>("columnsPanel")) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new ColumnsPanel(panelId, new ColumnsProxy());
            }
        };

        tabs.add(dataPanelTab);
        tabs.add(columnsPanelTab);

        // tabs.add(new AbstractTab(new Model<String>("second tab")) {
        // public Panel getPanel(String panelId) { return new TabPanel2(panelId); }
        // });
        add(new TabbedPanel("tabs", tabs));

    }
}
