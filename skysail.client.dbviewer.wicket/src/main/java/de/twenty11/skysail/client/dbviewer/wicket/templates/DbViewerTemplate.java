package de.twenty11.skysail.client.dbviewer.wicket.templates;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public abstract class DbViewerTemplate extends WebPage {

    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerTemplate() {
        add(new Label("title", "skysail dbviewer"));
    }
}
