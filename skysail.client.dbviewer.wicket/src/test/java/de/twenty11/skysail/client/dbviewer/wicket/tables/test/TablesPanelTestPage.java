package de.twenty11.skysail.client.dbviewer.wicket.tables.test;

import org.apache.wicket.markup.html.WebPage;

import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesPanel;
import de.twenty11.skysail.client.dbviewer.wicket.tables.TablesProxy;

public class TablesPanelTestPage extends WebPage {

    private static final long serialVersionUID = -7532754691242403980L;

    public TablesPanelTestPage(TablesProxy proxyMock) {
        add(new TablesPanel("tablesPanel", proxyMock));
    }

}
