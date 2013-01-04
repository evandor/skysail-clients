package de.twenty11.skysail.client.dbviewer.wicket.tables;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import de.twenty11.skysail.common.ext.dbviewer.TableDetails;

public class TablesPanel extends Panel {

    public static final String TABLES = "tables";

    private static final long serialVersionUID = 5884156412839557835L;
    private transient TablesProxy proxy;
    private Label errorMessage;

    public TablesPanel(String id, final TablesProxy proxy) {
        super(id);
        this.proxy = proxy;
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

        ListView<TableDetails> Tables = new TablesListView(TABLES, new TablesModel(this));

        add(Tables);
        add(errorMessage);
    }

    @Override
    protected void onConfigure() {
        // TODO Auto-generated method stub
        super.onConfigure();
    }

    public void setErrorMessage(String message) {
        errorMessage.setDefaultModelObject(message);
    }

    public TablesProxy getProxy() {
        return proxy;
    }

}
