package de.twenty11.skysail.client.dbviewer.wicket;

import org.apache.wicket.markup.html.WebPage;

public abstract class DbViewerTemplate extends WebPage {

    /**
     * 
     */
    private static final long serialVersionUID = 4791216526280042048L;

    public DbViewerTemplate() {
        // http://stackoverflow.com/questions/10683018/wicket-debugbar-devutils-during-production
        // if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()) {
        // add(new DebugBar("debug"));
        // } else {
        // add(new EmptyPanel("debug").setVisible(false));
        // }
    }
}
