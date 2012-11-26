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
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class ConnectionsModel {

    public List<Connection> getCategories() {
        List<Connection> result = new ArrayList<Connection>();
        ClientResource cr = new DbViewerClientResource();
        Class<RestfulConnections> clazs;
        RestfulConnections connections;
        // 1 .) RestfulConnections connections = cr.wrap(RestfulConnections.class);

        // 2. ) clazs = (Class<RestfulConnections>)
        // cr.getClass().getClassLoader().loadClass("de.twenty11.skysail.common.ext.dbviewer.RestfulConnections");
        // connections = cr.wrap(clazs);

        List<ConnectionDetails> data;
        
        Engine engine = Engine.getInstance();
        ClassLoader loaderOld = engine.getUserClassLoader();
        ClassLoader loaderNew = new ConnectionDetails().getClass().getClassLoader();
        try {
            engine.setUserClassLoader(loaderNew);
            connections = cr.wrap(RestfulConnections.class);
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<Response<List<ConnectionDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

            Response<List<ConnectionDetails>> connections2 = connections.getConnections();
            data = connections2.getData();
        } finally {
            engine.setUserClassLoader(loaderOld);
        }
        
        for (ConnectionDetails connectionDetails : data) {
            Connection conn = new Connection();
            conn.setName(connectionDetails.getName());
            result.add(conn);
        }
        // Connection category = new Connection();
        // category.setName("Programming");
        // connections.add(category);
        // Schema todo = new Schema("Write more about e4");
        // category.getSchemes().add(todo);
        // todo = new Schema("Android", "Write a widget.");
        // category.getSchemes().add(todo);
        //
        // category = new Connection();
        // category.setName("Leasure");
        // connections.add(category);
        // todo = new Schema("Skiing");
        // category.getSchemes().add(todo);

        return result;
    }

}
