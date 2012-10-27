package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionProxy;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionsProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.responses.Response;

public class ConnectionsPanel extends Panel {

    public static final String CONNECTIONS = "connections";

    private static final long serialVersionUID = 5884156412839557835L;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPanel.class);
    private ConnectionsProxy proxy;
    private Label errorMessage;
    private BookmarkablePageLink<String> newConnection;

    public ConnectionsPanel(String id, final ConnectionsProxy proxy) {
        super(id);
        this.proxy = proxy;
    }

    @SuppressWarnings("serial")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        adjustJacksonConverterForRestlet();

        errorMessage = new Label("errorMessage", new Model<String>("")) {
            public boolean isVisible() {
                return StringUtils.isNotEmpty((String) getDefaultModelObject());
            }
        };

        newConnection = new BookmarkablePageLink<String>("addConnection", ConnectionPage.class) {
            @Override
            public boolean isVisible() {
                return !errorMessage.isVisible();
            }
        };

        ListView<ConnectionDetails> connections = new ListView<ConnectionDetails>(CONNECTIONS, new ConnectionsModel(
                proxy, this)) {

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
                        logger.info("about to delete connection '{}'", connection.getId());
                        ConnectionProxy proxy = new ConnectionProxy();
                        Representation answer = proxy.deleteConnection(connection.getId());
                        setResponsePage(DbViewerHome.class);
                    }
                });
            }
        };

        add(connections);
        add(errorMessage);
        add(newConnection);

    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    private void adjustJacksonConverterForRestlet() {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<Response<List<ConnectionDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
    }

}
