package skysail.client.dbviewer.e4;

import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.resource.ClientResource;

public class DbViewerClientResource extends ClientResource {

    // TODO set from outside
    private static final String SERVER_URL = "http://localhost:2011/dbviewer/connections/";

    public DbViewerClientResource() {
        super(SERVER_URL + "?media=json");
        addAuthentication();
    }

    public DbViewerClientResource(String connectionId) {
        super(SERVER_URL + connectionId + "/schemas?media=json");
        addAuthentication();
    }

    private void addAuthentication() {
        //User user = DbViewerSession.get().getUser();
        //if (user != null) {
        User user = new User("scott","tiger");
            ChallengeResponse authentication = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getUsername(),
                    user.getPassword());
            setChallengeResponse(authentication);
        //}
    }

}
