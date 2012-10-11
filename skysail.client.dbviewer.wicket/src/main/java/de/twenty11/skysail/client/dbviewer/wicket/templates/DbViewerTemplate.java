package de.twenty11.skysail.client.dbviewer.wicket.templates;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public abstract class DbViewerTemplate extends WebPage {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerTemplate() {
        add(new Label("title", "skysail dbviewer"));
        // http://stackoverflow.com/questions/10683018/wicket-debugbar-devutils-during-production
        // if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()) {
        // add(new DebugBar("debug"));
        // } else {
        // add(new EmptyPanel("debug").setVisible(false));
        // }
    }
}
