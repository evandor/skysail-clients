package de.twenty11.skysail.client.osgimonitor.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
    	//getApplication().getFrameworkSettings().getVersion()
		add(new Label("projectName", "Skysail OSGi Monitor"));
    }
}
