package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.apache.wicket.WicketRuntimeException;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.Engine;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;

public class ConnectionsProxy {

    public RestfulConnections getRestfulConnections() {
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/"
                + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        // Class<RestfulConnections> classToLoad = (Class<RestfulConnections>)
        // clientResource.getClass().getClassLoader()
        // .loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
        try {

            Engine engine = Engine.getInstance();
            ClassLoader loaderOld = engine.getUserClassLoader();
            Class<RestfulConnections> classToLoad = (Class<RestfulConnections>) this.getClass().getClassLoader().loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
            ClassLoader loaderNew = classToLoad.getClass().getClassLoader();
            try {
                engine.setUserClassLoader(loaderNew);
                 RestfulConnections wrap = clientResource.wrap(classToLoad);
                 return wrap;
            } finally {
                engine.setUserClassLoader(loaderOld);
            }
            // Class<? extends Class> restfulConnectionsClass = RestfulConnections.class.getClass();
            // ClassLoader classLoader = restfulConnectionsClass.getClassLoader();
            // Class<?> loadClass = classLoader.loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
//            Class<RestfulConnections> classToLoad = (Class<RestfulConnections>) clientResource.getClass()
//                    .getClassLoader().loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
//            return clientResource.wrap(classToLoad);
        } catch (ClassNotFoundException e) {
            throw new WicketRuntimeException(e);
        }
    }
    
    public Representation addConnection(ConnectionDetails details) {
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/"
                + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        Representation post = clientResource.post(details);
        return post;
    }

}
