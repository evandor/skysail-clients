package de.twenty11.skysail.client.dbviewer.wicket.connections;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;

public class ConnectionsProxy {

    public RestfulConnections getRestfulConnections() {
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connections/"
                + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        return clientResource.wrap(RestfulConnections.class);
    }

}
