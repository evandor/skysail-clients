package de.twenty11.skysail.client.dbviewer.wicket;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

public class DbViewerClientResource extends ClientResource {

    // TODO set from outside
    private static final String SERVER_URL = "http://localhost:2012/dbviewer/connections/";

    public DbViewerClientResource() {
        super(SERVER_URL + "?media=json");
        addAuthentication();
    }

    public DbViewerClientResource(String connectionId) {
        super(SERVER_URL + connectionId + "?media=json");
        addAuthentication();
    }

    private void addAuthentication() {
        User user = DbViewerSession.get().getUser();
        if (user != null) {
            ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getUsername(),
                    user.getPassword());
            setChallengeResponse(authentication);
        }
    }

}
