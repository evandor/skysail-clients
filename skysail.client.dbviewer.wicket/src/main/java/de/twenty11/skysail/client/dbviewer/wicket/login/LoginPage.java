package de.twenty11.skysail.client.dbviewer.wicket.login;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.client.dbviewer.wicket.User;
import de.twenty11.skysail.client.dbviewer.wicket.templates.DbViewerTemplate;

@SuppressWarnings("serial")
public class LoginPage extends DbViewerTemplate {

    private Label messageLabel = new Label("message", new Model(""));

    private User user = new User("", "");

    public LoginPage() {
        createForm();
    }

    private void createForm() {
        Form form = new Form("form");
        add(form);
        form.add(new TextField<String>("username", new PropertyModel(user, "username")));
        form.add(new TextField<String>("password", new PropertyModel(user, "password")));
        form.add(messageLabel);

        form.add(new Button("login") {
            @Override
            public void onSubmit() {
                if (signIn(user.getUsername(), user.getPassword())) {
                    if (!continueToOriginalDestination()) {
                        setResponsePage(getApplication().getHomePage());
                    }
                } else {
                    error("Unknown username/ password");
                }
                setResponsePage(DbViewerHome.class);
            }

            private boolean signIn(String username, String password) {
                if (username != null && password != null) {
                    DbViewerSession.get().setUser(user);
                    return true;
                }
                return false;
            }

        });
    }
}
