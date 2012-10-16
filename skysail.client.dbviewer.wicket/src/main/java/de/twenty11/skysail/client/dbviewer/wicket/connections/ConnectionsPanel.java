package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

@SuppressWarnings("serial")
public class ConnectionsPanel extends Panel {

    public static final String CONNECTIONS = "connections";
    private Label connectionsMessage;

    public ConnectionsPanel(String id, ConnectionsProxy proxy) {
        super(id);
        
        connectionsMessage = new Label("connectionsMessage", new Model<String>(""));
        connectionsMessage.setVisible(false);

        //proxyMock.setErrorMessageLabel(connectionsMessage);
        
        ConnectionsModel model = new ConnectionsModel();
        model.setConnectionsProxy(proxy);
        model.setErrorMessageLabel(connectionsMessage);
        ListView<ConnectionDetails> connections = new ListView<ConnectionDetails>(CONNECTIONS, model) {
            @Override
            protected void populateItem(ListItem<ConnectionDetails> item) {
                ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
                item.add(new Label("connectionName", connection.getId()));
            }
        };

        add(connections);
        add(connectionsMessage);
    };

}
