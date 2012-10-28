package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.apache.wicket.WicketRuntimeException;
import org.restlet.engine.Engine;
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
        try {

            Engine engine = Engine.getInstance();
            ClassLoader loaderOld = engine.getUserClassLoader();
            Class<RestfulConnections> classToLoad = (Class<RestfulConnections>) this.getClass().getClassLoader()
                    .loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
            ClassLoader loaderNew = classToLoad.getClass().getClassLoader();
            try {
                engine.setUserClassLoader(loaderNew);
                RestfulConnections wrap = clientResource.wrap(classToLoad);
                return wrap;
            } finally {
                engine.setUserClassLoader(loaderOld);
            }
        } catch (ClassNotFoundException e) {
            throw new WicketRuntimeException(e);
        }
    }

    public Representation addConnection(ConnectionDetails details) {
        ClientResource clientResource = new DbViewerClientResource();
        Representation post = clientResource.post(details);
        return post;
    }

}
