package de.twenty11.skysail.client.dbviewer.wicket.templates;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.PropertyModel;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.client.dbviewer.wicket.User;
import de.twenty11.skysail.client.dbviewer.wicket.login.LoginPage;

public abstract class DbViewerTemplate extends WebPage {

    private static final long serialVersionUID = 4791216526280042048L;

    private static class LabelBean implements Serializable {

        String labelText = "Login";

        public String getLabelText() {
            return this.labelText;
        }

        public void setLabelText(final String labelText) {
            this.labelText = labelText;
        }

    }

    @SuppressWarnings("serial")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("title", "skysail dbviewer"));

        User user = DbViewerSession.get().getUser();
        final LabelBean labelBean = new LabelBean();
        if (user != null) {
            labelBean.setLabelText("Logout (" + user.getUsername() + ")");
        }
        add(new Link<Void>("login") {

            @Override
            public void onClick() {
                if (DbViewerSession.get().getUser() != null) {
                    DbViewerSession.get().setUser(null);
                    setResponsePage(DbViewerHome.class);
                } else {
                    setResponsePage(LoginPage.class);
                }
            }
        }.add(new Label("label", new PropertyModel<String>(labelBean, "labelText"))));
    }
}
