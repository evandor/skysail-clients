package de.twenty11.skysail.client.dbviewer.wicket.columns;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import de.twenty11.skysail.common.ext.dbviewer.ColumnsDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulColumns;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class ColumnsPanel extends Panel {

    public static final String COLUMNS = "columns";

    private static final long serialVersionUID = 5884156412839557835L;
    private transient ColumnsProxy proxy;
    private Label errorMessage;

    public ColumnsPanel(String id, final ColumnsProxy proxy) {
        super(id);
        this.proxy = proxy;
    }

    @SuppressWarnings("serial")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        errorMessage = new Label("errorMessage", new Model<String>("")) {
            public boolean isVisible() {
                return StringUtils.isNotEmpty((String) getDefaultModelObject());
            }
        };

        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<Response<List<ColumnsDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
        RestfulColumns restfulSchemas = proxy.getRestfulColumns();
        Response<List<ColumnsDetails>> response = restfulSchemas.getColumns();
        List<ColumnsDetails> data2 = response.getData();
        List<String> schemasList = new ArrayList<String>();
        for (ColumnsDetails schemaDetails : data2) {
            schemasList.add(schemaDetails.getId());
        }
        ListView<ColumnsDetails> connections = new ColumnsListView(COLUMNS, new ColumnsModel(this));

        add(connections);
        add(errorMessage);
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public ColumnsProxy getProxy() {
        return proxy;
    }

}
