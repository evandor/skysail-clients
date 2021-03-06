package de.twenty11.skysail.client.dbviewer.wicket.tables;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.common.ext.dbviewer.RestfulTables;
import de.twenty11.skysail.common.ext.dbviewer.TableDetails;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class TablesModel extends LoadableDetachableModel<List<TableDetails>> {

    private static final long serialVersionUID = -5645764949454058272L;
    private static final Logger logger = LoggerFactory.getLogger(TablesModel.class);
    private TablesPanel panel;

    public TablesModel(TablesPanel tablesPanel) {
        panel = tablesPanel;
    }

    @Override
    protected List<TableDetails> load() {
        try {
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<SkysailResponse<List<TableDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
            RestfulTables restfulConnections = panel.getProxy().getRestfulTables();
            SkysailResponse<List<TableDetails>> response = restfulConnections.getTables();
            logger.info("found {} tables", response.getData().size());
            return response.getData();
        } catch (Exception e) {
            panel.setErrorMessage(e.getMessage() != null ? e.getMessage() : "unknown error");
            return Collections.emptyList();
        }
    }
}
