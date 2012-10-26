package de.twenty11.skysail.client.dbviewer.wicket.connection;

import java.util.Map;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
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

        ConverterHelper myLocalJacksonConverter = new MyLocalJacksonCustomConverter(
                new TypeReference<Response<MapData>>() {
                });
        RestletUtils.replaceConverter(JacksonConverter.class, myLocalJacksonConverter);
        ConnectionProxy proxy = new ConnectionProxy();
        RestfulConnection<MapData> restfulConnection = proxy.getRestfulConnection(id.toString());
        Map<String, String> connection = (Map<String, String>) restfulConnection.getConnection().getData()
                .getDictionary();
        final ConnectionDetails connectionDetails = new ConnectionDetails((String) connection.get("id"), "", "", "", "");
        createForm(connectionDetails);
    }

    public ConnectionPage() {
        final ConnectionDetails connection = new ConnectionDetails("", "", "", "", "");
        createForm(connection);
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
