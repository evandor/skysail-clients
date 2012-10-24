package de.twenty11.skysail.client.dbviewer.wicket.proxies;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Method;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;

/**
 * Access point for remote server for connections
 *
 */
public class ConnectionProxy {

    private Object mapper;

    public RestfulConnection getRestfulConnection(String id) {
        
//        Request request = new Request(Method.GET, "http://localhost:8554/dbviewer/connections/" +
//                id + "?media=json");
//        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
//        request.setChallengeResponse(authentication);
        //Response response = new Response(request);
        //Response response = getClientResource().handleOutbound(request);
        //System.out.println(response);
//        String strRep = mapper.writeValueAsString(successResponse);
//        System.out.println(strRep);
//        return null;
        ClientResource clientResource = new ClientResource("http://localhost:8888/dbviewer/connections/" +
                id + "?media=json");
        ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, "scott", "tiger");
        clientResource.setChallengeResponse(authentication);
        RestfulConnection wrap = clientResource.wrap(RestfulConnection.class);
        return wrap;
    }
    
}
