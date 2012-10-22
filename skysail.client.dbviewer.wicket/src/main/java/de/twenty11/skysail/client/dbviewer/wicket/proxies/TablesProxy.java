package de.twenty11.skysail.client.dbviewer.wicket.proxies;

import org.apache.wicket.WicketRuntimeException;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.Engine;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.RestfulTables;

/**
 * Access point for remote server for connections
 * 
 */
public class TablesProxy {

    public RestfulTables getRestfulTables() {
        
        String connection = DbViewerSession.get().getSelectedConnection();
        if (connection == null) {
            return null;
        }
        
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/"
                + connection + "/?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        try {

            Engine engine = Engine.getInstance();
            ClassLoader loaderOld = engine.getUserClassLoader();
            Class<RestfulTables> classToLoad = (Class<RestfulTables>) this.getClass().getClassLoader()
                    .loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulTables");
            ClassLoader loaderNew = classToLoad.getClass().getClassLoader();
            try {
                engine.setUserClassLoader(loaderNew);
                RestfulTables wrap = clientResource.wrap(classToLoad);
                return wrap;
            } finally {
                engine.setUserClassLoader(loaderOld);
            }
        } catch (ClassNotFoundException e) {
            throw new WicketRuntimeException(e);
        }
    }
}
