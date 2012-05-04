package de.twenty11.skysail.client.osgimonitor.wicket;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;

import de.twenty11.skysail.client.osgimonitor.wicket.pages.bundles.ContactsDatabase;
import de.twenty11.skysail.client.osgimonitor.wicket.pages.home.HomePage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see de.twenty11.skysail.client.osgimonitor.wicket.Start#main(String[])
 */
public class WicketApplication extends WebApplication {

	private final ContactsDatabase contactsDB = new ContactsDatabase(50);

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<HomePage> getHomePage() {
		return HomePage.class;
	}

	@Override
	protected void init() {
		super.init();
		// initialize Spring
		getComponentInstantiationListeners().add(
				new SpringComponentInjector(this));

		// Autolink-Verhalten grundsätzlich einschalten
		// getMarkupSettings().setAutomaticLinking(true);
		getApplicationSettings().setDefaultMaximumUploadSize(
				Bytes.megabytes(50));
		getMarkupSettings().setDefaultMarkupEncoding("utf-8");
		getRequestCycleSettings().setResponseRequestEncoding("utf-8");
		getDebugSettings().setOutputMarkupContainerClassName(true);
		getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(true);
		getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(true);
		// mountBookmarkablePage("/home", BundlesPage.class);
		// mountBookmarkablePage("/gmap", GMapsPage.class);
		getResourceSettings().addResourceFolder("/html");
	}

	/**
	 * @return contacts database
	 */
	public ContactsDatabase getContactsDB() {
		return contactsDB;
	}

}
