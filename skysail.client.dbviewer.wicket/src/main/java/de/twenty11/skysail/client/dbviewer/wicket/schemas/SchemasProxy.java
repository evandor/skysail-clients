package de.twenty11.skysail.client.dbviewer.wicket.schemas;

import java.util.Collections;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.RestfulSchemas;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.responses.SuccessResponse;

/**
 * Access point for remote server for connections
 * 
 */
public class SchemasProxy {

    public RestfulSchemas getRestfulSchemas() {
        
        String connection = DbViewerSession.get().getActiveConnection();
        if (connection == null) {
            return new RestfulSchemas() {

                @Override
                @Get
                public Response<List<SchemaDetails>> getSchemas() {
                     List<SchemaDetails> emptyList = Collections.<SchemaDetails>emptyList();
                     return new SuccessResponse<List<SchemaDetails>>(emptyList);
                }
                
            };
        }
        
        ClientResource clientResource = new DbViewerClientResource(connection + "/schemas");
        return clientResource.wrap(RestfulSchemas.class);
    }


}
