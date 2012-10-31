package de.twenty11.skysail.client.dbviewer.wicket.schemas;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.model.LoadableDetachableModel;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.ext.dbviewer.RestfulSchemas;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;
import de.twenty11.skysail.common.responses.Response;

public class SchemasModel extends LoadableDetachableModel<List<SchemaDetails>> {

    private static final long serialVersionUID = -5645764949454058272L;
    private static final Logger logger = LoggerFactory.getLogger(SchemasModel.class);
    private SchemasPanel panel;

    public SchemasModel(SchemasPanel panel) {
        this.panel = panel;
    }

    @Override
    protected List<SchemaDetails> load() {
        try {
            ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                    new TypeReference<Response<List<SchemaDetails>>>() {
                    });
            RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
            RestfulSchemas restfulSchemas = panel.getProxy().getRestfulSchemas();
            Response<List<SchemaDetails>> response = restfulSchemas.getSchemas();
            logger.info("found {} schemas", response.getData().size());
            return response.getData();
        } catch (Exception e) {
            panel.setErrorMessage(e.getMessage());
            return Collections.emptyList();
        }
    }
}
