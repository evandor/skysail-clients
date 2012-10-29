package de.twenty11.skysail.client.dbviewer.wicket.tables;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.common.ext.dbviewer.RestfulTables;
import de.twenty11.skysail.common.responses.Response;

public class TablesModel extends LoadableDetachableModel<List<String>> {

    private static final long serialVersionUID = -5645764949454058272L;
    private static final Logger logger = LoggerFactory.getLogger(TablesModel.class);
    private TablesPanel panel;

    public TablesModel(TablesPanel tablesPanel) {
        panel = tablesPanel;
    }

    @Override
    protected List<String> load() {
        try {
            RestfulTables restfulConnections = panel.getProxy().getRestfulTables();
            Response<List<String>> response = restfulConnections.getTables();
            logger.info("found {} tables", response.getData().size());
            return response.getData();
        } catch (Exception e) {
            panel.setErrorMessage(e.getMessage() != null ? e.getMessage() : "unknown error");
            return Collections.emptyList();
        }
    }
}
