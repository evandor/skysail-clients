package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.responses.SkysailResponse;

public class ConnectionsModel extends LoadableDetachableModel<List<ConnectionDetails>> {

    private static final long serialVersionUID = 7234968751585133910L;
    private Label messageLabel;
    private ConnectionsProxy connectionsProxy;

    @Override
    protected List<ConnectionDetails> load() {

        try {
            RestfulConnections proxy = connectionsProxy.getRestfulConnections();
            SkysailResponse<GridData> connections = proxy.getConnections();
            return null;
        } catch (Exception e) {
            messageLabel.setVisible(true);
            messageLabel.getPage().get("connectionPanel:" + ConnectionsPanel.CONNECTIONS).setVisible(false);
            IModel<String> defaultModel = (IModel<String>) messageLabel.getDefaultModel();
            defaultModel.setObject(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void setErrorMessageLabel(Label connectionsMessage) {
        this.messageLabel = connectionsMessage;
    }

    public void setConnectionsProxy(ConnectionsProxy proxy) {
        this.connectionsProxy = proxy;
    }

}
