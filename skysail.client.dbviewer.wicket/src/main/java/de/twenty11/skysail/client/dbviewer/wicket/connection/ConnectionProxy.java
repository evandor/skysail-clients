package de.twenty11.skysail.client.dbviewer.wicket.connection;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;

/**
 * Access point for remote server for connections
 * 
 */
public class ConnectionProxy {

    public RestfulConnection getRestfulConnection(String id) {
        ClientResource clientResource = new DbViewerClientResource(id);
        return clientResource.wrap(RestfulConnection.class);
    }

    public Representation deleteConnection(String name) {
        ClientResource clientResource = new DbViewerClientResource(name);
        return clientResource.delete();
    }

}
