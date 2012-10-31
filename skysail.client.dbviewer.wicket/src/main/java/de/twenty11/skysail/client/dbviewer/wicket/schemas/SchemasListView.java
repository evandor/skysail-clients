package de.twenty11.skysail.client.dbviewer.wicket.schemas;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.restlet.representation.Representation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.twenty11.skysail.client.dbviewer.wicket.DbViewerHome;
import de.twenty11.skysail.client.dbviewer.wicket.DbViewerSession;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionPage;
import de.twenty11.skysail.client.dbviewer.wicket.connection.ConnectionProxy;
import de.twenty11.skysail.common.ext.dbviewer.ConnectionDetails;
import de.twenty11.skysail.common.ext.dbviewer.SchemaDetails;

public class SchemasListView extends ListView<SchemaDetails> {

    private static final long serialVersionUID = -6176963254976501980L;
    private static final Logger logger = LoggerFactory.getLogger(SchemasListView.class);

    public SchemasListView(String id, SchemasModel connectionsModel) {
        super(id, connectionsModel);
    }

    @Override
    protected void populateItem(ListItem<SchemaDetails> item) {
        final SchemaDetails connection = (SchemaDetails) item.getModelObject();
        item.add(new Label("schemaName", connection.getId()));
    }

}
