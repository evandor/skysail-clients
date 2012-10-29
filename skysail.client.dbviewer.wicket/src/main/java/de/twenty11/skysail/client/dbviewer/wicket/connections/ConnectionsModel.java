package de.twenty11.skysail.client.dbviewer.wicket.connections;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.responses.Response;

public class ConnectionsModel extends LoadableDetachableModel<List<ConnectionDetails>> {

    private static final long serialVersionUID = -5645764949454058272L;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionsModel.class);
    private ConnectionsPanel panel;

    public ConnectionsModel(ConnectionsPanel connectionsPanel) {
        panel = connectionsPanel;
    }

    @Override
    protected List<ConnectionDetails> load() {
        try {
            RestfulConnections restfulConnections = panel.getProxy().getRestfulConnections();
            Response<List<ConnectionDetails>> response = restfulConnections.getConnections();
            logger.info("found {} connections", response.getData().size());
            return response.getData();
        } catch (Exception e) {
            panel.setErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
}
