package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.responses.SkysailResponse;

@SuppressWarnings("serial")
public class ConnectionsPanel extends Panel {

    private static final String CONNECTIONS = "connections";
    private Label connectionsMessage;

    public ConnectionsPanel(String id) {
        super(id);
        ListView<ConnectionDetails> connections = new ListView<ConnectionDetails>(CONNECTIONS, new ConnectionsModel()) {
            @Override
            protected void populateItem(ListItem<ConnectionDetails> item) {
                ConnectionDetails connection = (ConnectionDetails) item.getModelObject();
                item.add(new Label("connectionName", connection.getId()));
            }
        };

        connectionsMessage = new Label("connectionsMessage", new Model<String>(""));
        connectionsMessage.setVisible(false);

        add(connections);
        add(connectionsMessage);
    };

    private class ConnectionsModel extends LoadableDetachableModel<List<ConnectionDetails>> {

        private static final long serialVersionUID = 7234968751585133910L;

        @Override
        protected List<ConnectionDetails> load() {

            try {
                ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/"
                        + "?media=json");
                ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
                clientResource.setChallengeResponse(authentication);
                RestfulConnections proxy = clientResource.wrap(RestfulConnections.class);
                SkysailResponse<GridData> connections = proxy.getConnections();
                return null;
            } catch (ResourceException e) {
                connectionsMessage.setVisible(true);
                get(CONNECTIONS).setVisible(false);
                IModel<String> defaultModel = (IModel<String>) connectionsMessage.getDefaultModel();
                defaultModel.setObject(e.getMessage());
                return Collections.emptyList();
            } catch (Exception e) {
                // TODO propagate exception
                // List<ConnectionDetails> result = new ArrayList<ConnectionDetails>();
                // result.add(new ConnectionDetails("id", "username", "password", "url", "driverClassName"));
                // result.add(new ConnectionDetails("id2", "username2", "password2", "url2", "driverClassName"));
                // return result;
                return Collections.emptyList();
            }
        }

    }

}
