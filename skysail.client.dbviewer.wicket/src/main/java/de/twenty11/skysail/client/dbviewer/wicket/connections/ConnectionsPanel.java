package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.pages.ConnectionPage;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.responses.Response;

@SuppressWarnings("serial")
public class ConnectionsPanel extends Panel {

    public static final String CONNECTIONS = "connections";
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsPanel.class);

    public ConnectionsPanel(String id, final ConnectionsProxy proxy) {
        super(id);
        
        final Label connectionsMessage = new Label("connectionsMessage", new Model<String>(""));
        connectionsMessage.setVisible(false);
        final BookmarkablePageLink<String> addNewConnectionButton = new BookmarkablePageLink<String>("addConnection", ConnectionPage.class);
        
        LoadableDetachableModel<List<ConnectionDetails>> loadableDetachableModel = new LoadableDetachableModel<List<ConnectionDetails>>() {

            @Override
            protected List<ConnectionDetails> load() {
                try {
                    RestfulConnections restfulConnections = proxy.getRestfulConnections();
                    Response<List<ConnectionDetails>> response = restfulConnections.getConnections();
                    logger.info("found {} connections", response.getData().size());
                    return response.getData();
                } catch (Exception e) {
                    //logger.error("Exception thrown trying to access skysail server: {}", e.getMessage(), e);
                    connectionsMessage.setVisible(true);
                    IModel<String> defaultModel = (IModel<String>) connectionsMessage.getDefaultModel();
                    defaultModel.setObject(e.getMessage());
                    addNewConnectionButton.setVisible(false);
                    return Collections.emptyList();
                }
            }
            
        };
        
        ListView<ConnectionDetails> connections = new ListView<ConnectionDetails>(CONNECTIONS, loadableDetachableModel) {
            @Override
            protected void populateItem(ListItem<ConnectionDetails> item) {
                ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
                item.add(new Label("connectionName", connection.getId()));
            }
        };

        add(connections);
        add(connectionsMessage);
        add(addNewConnectionButton);
    }

//    public Label getMessageLabel() {
//        return connectionsMessage;
//    };

}
