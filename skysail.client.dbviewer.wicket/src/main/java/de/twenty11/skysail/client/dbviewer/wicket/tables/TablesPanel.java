package de.twenty11.skysail.client.dbviewer.wicket.tables;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.proxies.TablesProxy;
import de.twenty11.skysail.common.ext.dbviewer.RestfulTables;
import de.twenty11.skysail.common.responses.Response;

@SuppressWarnings("serial")
public class TablesPanel extends Panel {

    public static final String TABLES = "tables";

    private static final Logger logger = LoggerFactory.getLogger(TablesPanel.class);

    public TablesPanel(String id, final TablesProxy proxy) {
        super(id);

        final Label panelMessage = new Label("tablesMessage", new Model<String>(""));
        panelMessage.setVisible(false);

        LoadableDetachableModel<List<String>> panelModel = new LoadableDetachableModel<List<String>>() {

            @Override
            protected List<String> load() {
                try {
                    RestfulTables restfulConnections = proxy.getRestfulTables();
                    Response<List<String>> response = restfulConnections.getTables();
                    logger.info("found {} tables", response.getData().size());
                    return response.getData();
                } catch (Exception e) {
                    // logger.error("Exception thrown trying to access skysail server: {}", e.getMessage(), e);
                    panelMessage.setVisible(true);
                    IModel<String> defaultModel = (IModel<String>) panelMessage.getDefaultModel();
                    defaultModel.setObject(e.getMessage());
                    return Collections.emptyList();
                }
            }

        };

        ListView<String> connections = new ListView<String>(TABLES, panelModel) {
            @Override
            protected void populateItem(ListItem<String> item) {
                String modelObject = item.getModelObject();
                item.add(new Label("tableName", modelObject));
            }
        };

        add(connections);
        add(panelMessage);
    }

}
