package de.twenty11.skysail.client.osgimonitor.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.twenty11.skysail.client.osgimonitor.wicket.nav.MainNavigationPanel;

public abstract class Template extends WebPage {

	private static final long serialVersionUID = 1L;

    public Template(final PageParameters parameters) {
		add(new Label("projectName", "Skysail OSGi Monitor"));
		add(new MainNavigationPanel("navigation"));
    }
}
