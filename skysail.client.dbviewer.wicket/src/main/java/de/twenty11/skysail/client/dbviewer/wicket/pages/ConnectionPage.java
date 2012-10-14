package de.twenty11.skysail.client.dbviewer.wicket.pages;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;

@SuppressWarnings("serial")
public class ConnectionPage extends DbViewerTemplate {

    public ConnectionPage() {
        Form form = new Form("form");
        add(form);

        ConnectionDetails connection = new ConnectionDetails("", "", "", "", "");

        form.add(new TextField("id", new PropertyModel(connection, "id")));
        form.add(new TextField("username", new PropertyModel(connection, "username")));

        form.add(new Button("order") {
            @Override
            public void onSubmit() {
                // return to front page
                setResponsePage(DbViewerHome.class);
            }
        });
    }
}
