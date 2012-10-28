package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

public class ConnectionsListView extends ListView<ConnectionDetails> {

    private static final long serialVersionUID = -6176963254976501980L;

    public ConnectionsListView(String id, ConnectionsModel connectionsModel) {
        super(id, connectionsModel);
    }

    @Override
    protected void populateItem(ListItem<ConnectionDetails> item) {
        final ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
        item.add(new Label("connectionName", connection.getId()));
        PageParameters params = new PageParameters();
        params.add("id", connection.getId());
        item.add(new BookmarkablePageLink<String>("edit", ConnectionPage.class, params));
        item.add(new BookmarkablePageLink<String>("select", ConnectionPage.class, params));
        item.add(new Link<String>("delete") {

            @Override
            public void onClick() {
                // logger.info("about to delete connection '{}'", connection.getId());
                ConnectionProxy proxy = new ConnectionProxy();
                Representation answer = proxy.deleteConnection(connection.getId());
                setResponsePage(DbViewerHome.class);
            }
        });
    }

}
