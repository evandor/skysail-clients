package de.twenty11.skysail.client.dbviewer.wicket.proxies;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;

/**
 * Access point for remote server for connections
 *
 */
public class ConnectionProxy {

    public RestfulConnection getRestfulConnection(String id) {
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/" +
                id + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        RestfulConnection wrap = clientResource.wrap(RestfulConnection.class);
        return wrap;
    }
    
}
