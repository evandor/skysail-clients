package de.twenty11.skysail.client.dbviewer.wicket.schemas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.OnChangeAjaxBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.common.ext.dbviewer.RestfulSchemas;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;
import de.twenty11.skysail.common.responses.SkysailResponse;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class SchemasPanel extends Panel {

    public static final String SCHEMAS = "schemas";

    private static final long serialVersionUID = 5884156412839557835L;
    private transient SchemasProxy proxy;
    private Label errorMessage;

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
                new TypeReference<SkysailResponse<List<SchemaDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

        List<String> schemasList = new ArrayList<String>();
        try {
            RestfulSchemas restfulSchemas = proxy.getRestfulSchemas();
            SkysailResponse<List<SchemaDetails>> response = restfulSchemas.getSchemas();
            List<SchemaDetails> data2 = response.getData();
            if (data2 != null) {
                for (SchemaDetails schemaDetails : data2) {
                    schemasList.add(schemaDetails.getId());
                }
            }
        } catch (Exception e) {
            errorMessage.setDefaultModelObject(e.getMessage());
        }

        DropDownChoice<String> dropDownChoice = new DropDownChoice<String>("schemasDropDown", new IModel<String>() {

            private String schema;

            @Override
            public void detach() {
            }

            @Override
            public String getObject() {
                return schema;
            }

            @Override
            public void setObject(String object) {
                this.schema = object;
            }
        }, schemasList) {
            @Override
            public boolean isEnabled() {
                return getChoices().size() > 0;
            }
        };
        dropDownChoice.setOutputMarkupId(true);
        dropDownChoice.add(new OnChangeAjaxBehavior() {

            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                Component component2 = getComponent();
                String schema = component2.getDefaultModelObjectAsString();
                DbViewerSession.get().setActiveSchema(schema);
                target.add(getPage());
            }
        });

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
