package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;

/**
 * Access point for remote server for connections
 * 
 */
public class ConnectionsProxy {

    public RestfulConnections getRestfulConnections() {
        ClientResource clientResource = new DbViewerClientResource();
        return clientResource.wrap(RestfulConnections.class);
    }

    public Representation addConnection(ConnectionDetails details) {
        ClientResource clientResource = new DbViewerClientResource();
        return clientResource.post(details);
    }

}
