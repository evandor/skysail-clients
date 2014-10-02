package de.twenty11.skysail.client.cli.test;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;

public class TestHelper {

    public static OsgiBundleXmlApplicationContext getAppConfig(Bundle skysailClientBundle) {

        ServiceReference<ApplicationContext> appConfigServiceRef = skysailClientBundle.getBundleContext()
                .getServiceReference(org.springframework.context.ApplicationContext.class);
        if (appConfigServiceRef == null) {
            return null;
        }
        OsgiBundleXmlApplicationContext appConfig = (OsgiBundleXmlApplicationContext) skysailClientBundle
                .getBundleContext().getService(appConfigServiceRef);
        return appConfig;

    }

}
