package de.twenty11.skysail.client.dbviewer.wicket.columns;

import java.util.Collections;
import java.util.List;

import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerClientResource;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.ColumnsDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulColumns;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.responses.SuccessResponse;

/**
 * Access point for remote server for connections
 * 
 */
public class ColumnsProxy {

    public RestfulColumns getRestfulColumns() {

        String connection = DbViewerSession.get().getActiveConnection();
        String schema = DbViewerSession.get().getActiveSchema();
        String table = DbViewerSession.get().getActiveTable();
        if (table == null) {
            return new RestfulColumns() {

                @Override
                @Get
                public SkysailResponse<List<ColumnsDetails>> getColumns() {
                    List<ColumnsDetails> emptyList = Collections.<ColumnsDetails> emptyList();
                    return new SuccessResponse<List<ColumnsDetails>>(emptyList);
                }

            };
        }

        ClientResource clientResource = new DbViewerClientResource("/" + connection + "/schemas/" + schema + "/tables/"
                + table + "/columns");
        return clientResource.wrap(RestfulColumns.class);
    }

}
