package de.twenty11.skysail.client.dbviewer.wicket.tables;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class TablesPanel extends Panel {

    public static final String TABLES = "tables";
    private static final Logger logger = LoggerFactory.getLogger(TablesPanel.class);
    private transient TablesProxy proxy;
    private Label errorMessage;

    public TablesPanel(String id, final TablesProxy proxy) {
        super(id);
        this.proxy = proxy;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        
        errorMessage = new Label("errorMessage", new Model<String>("")) {
            @Override
            public boolean isVisible() {
                return StringUtils.isNotEmpty((String) getDefaultModelObject());
            }
        };

        ListView<String> connections = new TablesListView(TABLES, new TablesModel(this));

        add(connections);
        add(errorMessage);
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public TablesProxy getProxy() {
        return proxy;
    }

}
