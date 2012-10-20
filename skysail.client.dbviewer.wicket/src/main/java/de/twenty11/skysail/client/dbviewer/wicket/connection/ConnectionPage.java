package de.twenty11.skysail.client.dbviewer.wicket.connection;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.restlet.representation.Representation;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.connections.ConnectionsProxy;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

@SuppressWarnings("serial")
public class ConnectionPage extends DbViewerTemplate {

    public ConnectionPage() {
        Form form = new Form("form");
        add(form);

        final ConnectionDetails connection = new ConnectionDetails("", "", "", "", "");

        form.add(new TextField("id", new PropertyModel(connection, "id")));
        form.add(new TextField("username", new PropertyModel(connection, "username")));

        form.add(new Button("order") {
            @Override
            public void onSubmit() {
                Representation answer = new ConnectionsProxy().addConnection(connection);
                setResponsePage(DbViewerHome.class);
            }
        });
    }
}
