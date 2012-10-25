package de.twenty11.skysail.client.dbviewer.wicket.connection;

import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionProxy;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.MapData;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;
import de.twenty11.skysail.common.responses.Response;

@SuppressWarnings("serial")
public class ConnectionPage extends DbViewerTemplate {

    public ConnectionPage(PageParameters parameters) {
        StringValue id = parameters.get("id");
        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter();
        replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
        ConnectionProxy proxy = new ConnectionProxy();
        RestfulConnection<MapData> restfulConnection = proxy.getRestfulConnection(id.toString());
        Response<MapData> connection2 = restfulConnection.getConnection();
        MapData data2 = connection2.getData();
        Map<String, String> dictionary = data2.getDictionary();
        Map connection = (Map) restfulConnection.getConnection().getData().getDictionary();
        final ConnectionDetails connectionDetails = new ConnectionDetails((String) connection.get("id"), "", "", "", "");
        createForm(connectionDetails);
    }

    public ConnectionPage() {
        final ConnectionDetails connection = new ConnectionDetails("", "", "", "", "");
        createForm(connection);
    }

    static void replaceConverter(Class<? extends ConverterHelper> converterClass, ConverterHelper newConverter) {

        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i).getClass().equals(converterClass)) {
                // oldConverter = converters.get(i);
                converters.set(i, newConverter);
                break;
            }
        }

        // if (oldConverter == null) {
        // logger.debug("Added {} to Restlet Engine", newConverter.getClass());
        // } else {
        // logger.debug("Replaced {} with {} in Restlet Engine", oldConverter.getClass(), newConverter.getClass());
        // }
    }

    private void createForm(final ConnectionDetails connection) {
        Form form = new Form("form");
        add(form);
        form.add(new TextField("id", new PropertyModel(connection, "id")));
        form.add(new TextField("username", new PropertyModel(connection, "username")));
        form.add(new TextField("password", new PropertyModel(connection, "password")));

        form.add(new Button("order") {
            @Override
            public void onSubmit() {
                Representation answer = new ConnectionsProxy().addConnection(connection);
                setResponsePage(DbViewerHome.class);
            }
        });
    }
}
