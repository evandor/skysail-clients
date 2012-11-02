package de.twenty11.skysail.client.dbviewer.wicket.tables;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;

public class TablesListView extends ListView<String> {

    private static final long serialVersionUID = -6176963254976501980L;
    private static final Logger logger = LoggerFactory.getLogger(TablesListView.class);

    public TablesListView(String id, TablesModel model) {
        super(id, model);
    }

    @Override
    protected void populateItem(ListItem<String> item) {
        final String table = (String) item.getModelObject();
        item.add(new Label("tableName", table));

        item.add(new Link<String>("select", new Model("") {
        }) {
            @Override
            public void onClick() {
                logger.info("setting active schema to '{}'", table);
                DbViewerSession.get().setActiveTable(table);
                setResponsePage(DbViewerHome.class);
            }
        });

    }

}
