package de.twenty11.skysail.client.dbviewer.wicket.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import de.twenty11.skysail.client.dbviewer.wicket.columns.ColumnsProxy;
import de.twenty11.skysail.common.ext.dbviewer.ColumnsDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulColumns;
import de.twenty11.skysail.common.responses.Response;
import de.twenty11.skysail.common.utils.MyLocalJacksonCustomConverter;
import de.twenty11.skysail.common.utils.RestletUtils;

public class DataPanel extends Panel {

    public static final String COLUMNS = "columns";

    private static final long serialVersionUID = 5884156412839557835L;
    private transient ColumnsProxy proxy;
    private Label errorMessage;

    public DataPanel(String id, final ColumnsProxy columnsProxy) {
        super(id);
        this.proxy = columnsProxy;
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

        final DataProvider dataProvider = new DataProvider();

        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<Response<List<ColumnsDetails>>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);

        ColumnsProxy columnsProxy = new ColumnsProxy();
        RestfulColumns restfulColumns = columnsProxy.getRestfulColumns();
        Response<List<ColumnsDetails>> columns2 = restfulColumns.getColumns();
        List<IColumn> columns = new ArrayList<IColumn>();
        if (columns2 != null && columns2.getData() != null && columns2.getData().size() > 0) {
            for (ColumnsDetails column : columns2.getData()) {
                columns.add(new PropertyColumn(new Model(column.getId()), column.getId(), column.getId()));
            }
        } else {
            columns.add(new PropertyColumn(new Model("First Name"), "name", "name"));
            columns.add(new PropertyColumn(new Model("Last Name"), "name", "name"));
            columns.add(new PropertyColumn(new Model("Last Name again"), "name", "name"));
        }
        DefaultDataTable table = new DefaultDataTable("datatable", columns, dataProvider, 10);

        add(table);

        add(errorMessage);
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public ColumnsProxy getProxy() {
        return proxy;
    }

}
