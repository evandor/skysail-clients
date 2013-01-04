package de.twenty11.skysail.client.dbviewer.wicket.tables;

import java.util.Collections;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.RestfulTables;
import de.twenty11.skysail.common.ext.dbviewer.TableDetails;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.responses.SuccessResponse;

/**
 * Access point for remote server for connections
 * 
 */
public class TablesProxy {

    public RestfulTables getRestfulTables() {
        
        String connection = DbViewerSession.get().getActiveConnection();
        String schema = DbViewerSession.get().getActiveSchema();
        if (connection == null || schema == null) {
            return new RestfulTables() {

                @Override
                @Get
                public Response<List<TableDetails>> getTables() {
                    List<TableDetails> emptyList = Collections.<TableDetails> emptyList();
                    return new SuccessResponse<List<TableDetails>>(emptyList);
                }
                
            };
        }

        ClientResource clientResource = new DbViewerClientResource(connection + "/schemas/" + schema + "/tables");
        return clientResource.wrap(RestfulTables.class);
    }
}
