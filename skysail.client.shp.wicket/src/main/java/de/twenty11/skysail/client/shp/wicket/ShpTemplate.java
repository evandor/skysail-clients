package de.twenty11.skysail.client.shp.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public abstract class ShpTemplate extends WebPage {

    private static final long serialVersionUID = 4791216526280042048L;

    @SuppressWarnings("serial")
    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("title", "skysail dbviewer"));

    }
}
