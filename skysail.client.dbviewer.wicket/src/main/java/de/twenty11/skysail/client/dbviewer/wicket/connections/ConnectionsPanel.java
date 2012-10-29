package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.responses.Response;

public class ConnectionsPanel extends Panel {

    public static final String CONNECTIONS = "connections";

    private static final long serialVersionUID = 5884156412839557835L;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPanel.class);
    private transient ConnectionsProxy proxy;
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

        ListView<ConnectionDetails> connections = new ConnectionsListView(CONNECTIONS,
                new ConnectionsModel(this));

        add(connections);
        add(errorMessage);
        add(newConnection);
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public ConnectionsProxy getProxy() {
        return proxy;
    }

    private void adjustJacksonConverterForRestlet() {
        @SuppressWarnings({ "rawtypes", "unchecked" })
        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<Response<List<ConnectionDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
    }

}
