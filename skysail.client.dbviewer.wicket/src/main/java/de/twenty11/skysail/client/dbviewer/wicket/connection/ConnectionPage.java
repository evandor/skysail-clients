package de.twenty11.skysail.client.dbviewer.wicket.connection;

import java.io.IOException;
import java.util.Map;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.RestletUtils;
import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.MapData;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;
import de.twenty11.skysail.common.responses.Response;

@SuppressWarnings("serial")
public class ConnectionPage extends DbViewerTemplate {

    private Label messageLabel = new Label("message", new Model(""));

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
        form.add(messageLabel);

        form.add(new Button("order") {
            @Override
            public void onSubmit() {
                Representation answer = new ConnectionsProxy().addConnection(connection);
                try {
                    String jsonString = answer.getText();
                    Response<?> response = new ObjectMapper().readValue(jsonString, new TypeReference<Response<?>>() {
                    });
                    if (!response.getSuccess()) {
                        messageLabel.setDefaultModel(new Model(response.getMessage()));
                        setResponsePage(getPage());
                        return;
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                setResponsePage(DbViewerHome.class);
            }
        });
    }
}
