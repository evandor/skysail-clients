package de.twenty11.skysail.client.dbviewer.wicket.data;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.common.ext.dbviewer.ColumnsDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulColumns;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class ColumnsModel extends LoadableDetachableModel<List<ColumnsDetails>> {

    private static final long serialVersionUID = -5645764949454058272L;
    private static final Logger logger = LoggerFactory.getLogger(ColumnsModel.class);
    private DataPanel panel;

    public ColumnsModel(DataPanel panel) {
        this.panel = panel;
    }

    @Override
    protected List<ColumnsDetails> load() {
        try {
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<SkysailResponse<List<ColumnsDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
            RestfulColumns restfulSchemas = panel.getProxy().getRestfulColumns();
            SkysailResponse<List<ColumnsDetails>> response = restfulSchemas.getColumns();
            logger.info("found {} schemas", response.getData().size());
            return response.getData();
        } catch (Exception e) {
            panel.setErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
}
