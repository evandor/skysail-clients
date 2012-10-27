package de.twenty11.skysail.client.dbviewer.wicket.proxies;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;

/**
 * Access point for remote server for connections
 * 
 */
public class ConnectionProxy {

    private Object mapper;

    public RestfulConnection getRestfulConnection(String id) {

        // Request request = new Request(Method.GET, "http://localhost:8554/dbviewer/connections/" +
        // id + "?media=json");
        // ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        // request.setChallengeResponse(authentication);
        // Response response = new Response(request);
        // Response response = getClientResource().handleOutbound(request);
        ClientResource clientResource = new ClientResource("http://localhost:8888/dbviewer/connections/" + id
                + "?media=json");
        // RestfulConnection create = ClientResource.create("http://localhost:8888/dbviewer/connections/" + id
        // + "?media=json", RestfulConnection.class);
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        RestfulConnection wrap = clientResource.wrap(RestfulConnection.class);
        return wrap;
    }

    public Representation deleteConnection(String name) {
        ClientResource clientResource = new ClientResource("http://localhost:8554/dbviewer/connection/" + name
                + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        Representation post = clientResource.delete();
        return post;
    }

}
