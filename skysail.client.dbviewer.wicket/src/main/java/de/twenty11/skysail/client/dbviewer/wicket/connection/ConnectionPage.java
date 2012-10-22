package de.twenty11.skysail.client.dbviewer.wicket.connection;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionProxy;
import de.twenty11.skysail.client.dbviewer.wicket.proxies.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.MapData;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.RestfulConnection;

@SuppressWarnings("serial")
public class ConnectionPage extends DbViewerTemplate {
    
    public ConnectionPage(PageParameters parameters) {
        StringValue id = parameters.get("id");
        ConnectionProxy proxy = new ConnectionProxy();
        RestfulConnection restfulConnection = proxy.getRestfulConnection(id.toString());
        MapData connection = restfulConnection.getConnection().getData();
        final ConnectionDetails connectionDetails = new ConnectionDetails(connection.getValue("id"), "", "", "", "");
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
