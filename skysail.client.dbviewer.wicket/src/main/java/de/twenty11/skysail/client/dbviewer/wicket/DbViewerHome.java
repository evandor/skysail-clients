package de.twenty11.skysail.client.dbviewer.wicket;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.twenty11.skysail.client.dbviewer.wicket.pages.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

public class DbViewerHome extends DbViewerTemplate {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerHome() {
        add(new Label("oneComponent", "Welcome7"));
        add(new ListView("connections", getConnections()) {
            @Override
            protected void populateItem(ListItem item) {
                ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
                item.add(new Label("connectionName", connection.getId()));
            }
        });
        add(new BookmarkablePageLink("addConnection", ConnectionPage.class));
    }

    private List<ConnectionDetails> getConnections() {
        return null;
    }
}
