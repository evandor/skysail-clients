package skysail.client.dbviewer.e4;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnections;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class ConnectionsModel {

    public List<Connection> getConnections() {
        List<Connection> result = new ArrayList<Connection>();
        ClientResource cr = new DbViewerClientResource();
        RestfulConnections connections;

        List<ConnectionDetails> data;
        
        Engine engine = Engine.getInstance();
        ClassLoader loaderOld = engine.getUserClassLoader();
        ClassLoader loaderNew = new ConnectionDetails().getClass().getClassLoader();
        try {
            engine.setUserClassLoader(loaderNew);
            connections = cr.wrap(RestfulConnections.class);
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<SkysailResponse<List<ConnectionDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

            SkysailResponse<List<ConnectionDetails>> connections2 = connections.getConnections();
            data = connections2.getData();
        } finally {
            engine.setUserClassLoader(loaderOld);
        }
        
        for (ConnectionDetails connectionDetails : data) {
            Connection conn = new Connection();
            conn.setName(connectionDetails.getName());
            result.add(conn);
        }
        return result;
    }

}
