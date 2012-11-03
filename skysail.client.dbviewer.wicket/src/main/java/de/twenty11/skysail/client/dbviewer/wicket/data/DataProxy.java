package de.twenty11.skysail.client.dbviewer.wicket.data;

import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.RestfulData;
import de.twenty11.skysail.common.grids.GridData;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.responses.SuccessResponse;

/**
 * Access point for remote server for connections
 * 
 */
public class DataProxy {

    public RestfulData getRestfulData() {

        String connection = DbViewerSession.get().getActiveConnection();
        String schema = DbViewerSession.get().getActiveSchema();
        String table = DbViewerSession.get().getActiveTable();
        if (table == null) {
            return new RestfulData() {

                @Override
                @Get
                public Response<GridData> getData() {
                    return new SuccessResponse<GridData>();
                }

            };
        }

        ClientResource clientResource = new DbViewerClientResource(connection + "/schemas/" + schema + "/tables/"
                + table + "/data");
        return clientResource.wrap(RestfulData.class);
    }

}
