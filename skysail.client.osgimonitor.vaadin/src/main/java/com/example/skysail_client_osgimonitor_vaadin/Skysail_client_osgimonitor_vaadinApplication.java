package com.example.skysail_client_osgimonitor_vaadin;

import com.vaadin.Application;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class Skysail_client_osgimonitor_vaadinApplication extends Application {
	@Override
	public void init() {
		Window mainWindow = new Window("Skysail_client_osgimonitor_vaadin Application");
        Label label = new Label("Hello Skysail user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
