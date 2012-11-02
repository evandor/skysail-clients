package de.twenty11.skysail.client.dbviewer.wicket.columns;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.twenty11.skysail.common.ext.dbviewer.ColumnsDetails;

public class ColumnsListView extends ListView<ColumnsDetails> {

    private static final long serialVersionUID = -6176963254976501980L;

    public ColumnsListView(String id, ColumnsModel connectionsModel) {
        super(id, connectionsModel);
    }

    @Override
    protected void populateItem(ListItem<ColumnsDetails> item) {
        final ColumnsDetails schema = (ColumnsDetails) item.getModelObject();
        item.add(new Label("columnName", schema.getId()));
    }

}
