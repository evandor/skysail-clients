package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

public class ConnectionsListView extends ListView<ConnectionDetails> {

    private static final long serialVersionUID = -6176963254976501980L;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsListView.class);

    public ConnectionsListView(String id, ConnectionsModel connectionsModel) {
        super(id, connectionsModel);
    }

    @SuppressWarnings("serial")
    @Override
    protected void populateItem(ListItem<ConnectionDetails> item) {
        final ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
        item.add(new Label("connectionName", connection.getName()));
        PageParameters params = new PageParameters();
        params.add("name", connection.getName());
        item.add(new BookmarkablePageLink<String>("edit", ConnectionPage.class, params));

        item.add(new Link<String>("select", new Model("hi") {
        }) {
            @Override
            public void onClick() {
                logger.info("setting active connection to '{}'", connection.getName());
                DbViewerSession.get().setActiveConnection(connection.getName());
                setResponsePage(DbViewerHome.class);
            }
        });

        item.add(new Link<String>("delete") {
            @Override
            public void onClick() {
                logger.info("about to delete connection '{}'", connection.getName());
                ConnectionProxy proxy = new ConnectionProxy();
                Representation answer = proxy.deleteConnection(connection.getName());
                setResponsePage(DbViewerHome.class);
            }
        });
    }

}
