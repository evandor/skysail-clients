package de.twenty11.skysail.client.dbviewer.wicket.schemas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulSchemas;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;
import de.twenty11.skysail.common.responses.Response;

public class SchemasPanel extends Panel {

    public static final String SCHEMAS = "schemas";

    private static final long serialVersionUID = 5884156412839557835L;
    private transient SchemasProxy proxy;
    private Label errorMessage;
    private BookmarkablePageLink<String> newConnection;

    public SchemasPanel(String id, final SchemasProxy proxy) {
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
                new TypeReference<Response<List<SchemaDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
        RestfulSchemas restfulSchemas = proxy.getRestfulSchemas();
        Response<List<SchemaDetails>> response = restfulSchemas.getSchemas();
        List<SchemaDetails> data2 = response.getData();
        List<String> schemasList = new ArrayList<String>();
        for (SchemaDetails schemaDetails : data2) {
            schemasList.add(schemaDetails.getId());
        }
        //List<String> schemasList = Arrays.asList(new String[] {"The Server Side", "Java Lobby", "Java.Net" });
        DropDownChoice<String> dropDownChoice = new DropDownChoice<String>("schemasDropDown", schemasList);

        
        ListView<SchemaDetails> connections = new SchemasListView(SCHEMAS,
                new SchemasModel(this));

        add(connections);
        add(errorMessage);
        add(dropDownChoice);
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public SchemasProxy getProxy() {
        return proxy;
    }

}
