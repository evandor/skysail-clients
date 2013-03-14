package skysail.client.dbviewer.e4;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.resource.ClientResource;

import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulSchemas;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class SchemesModel {

    public List<Schema> getSchemes(Connection connection) {
        List<Schema> result = new ArrayList<Schema>();
        ClientResource cr = new DbViewerClientResource(connection.getName());
        RestfulSchemas schemas;

        List<SchemaDetails> data;
        
        Engine engine = Engine.getInstance();
        ClassLoader loaderOld = engine.getUserClassLoader();
        ClassLoader loaderNew = new ConnectionDetails().getClass().getClassLoader();
        try {
            engine.setUserClassLoader(loaderNew);
            schemas = cr.wrap(RestfulSchemas.class);
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<SkysailResponse<List<SchemaDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

            SkysailResponse<List<SchemaDetails>> connections2 = schemas.getSchemas();
            data = connections2.getData();
        } finally {
            engine.setUserClassLoader(loaderOld);
        }
        
        for (SchemaDetails schemaDetails : data) {
            Schema entity = new Schema(schemaDetails.getId());
            result.add(entity);
        }
        return result;
    }

}
