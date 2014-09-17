package de.twenty11.skysail.client.cli.test;

import java.net.URL;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.shell.core.CommandResult;

public class RestCommands2Test extends TestBase {
    
    private static final Logger logger = LoggerFactory.getLogger(RestCommands2Test.class);

    @Test
    // @Ignore // LargeTest
    public void testName2() throws Exception {
        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");
        //
        // Bundle springContextBundle =
        // FrameworkUtil.getBundle(org.springframework.context.ApplicationContext.class);
        // URL shemasUrl =
        // springContextBundle.getEntry("META-INF/spring.schemas");

        OsgiBundleXmlApplicationContext appConfig = getAppContextOnceAvailable(skysailClientBundle, 500, 5);
        MyBootstrap bootstrap = new MyBootstrap(appConfig, new String[0],
                new String[] { shellPluginUrl.toExternalForm() });
        
        Bundle[] bundles = skysailClientBundle.getBundleContext().getBundles();
        for (Bundle bundle : bundles) {
            System.out.println("running: " + bundle.getSymbolicName() + " '" + bundle.getVersion() + "': " + bundle.getState());
        }
        
        
        shell = bootstrap.getJLineShellComponent();
        CommandResult exec = exec("get", "assert --status 300");
    }

    private OsgiBundleXmlApplicationContext getAppContextOnceAvailable(Bundle skysailClientBundle,
            int waitingMilliSecons, int retries) {
        for (int i = 0; i < retries; i++) {
            logger.info("waiting for appContext to become available #" + i);
            OsgiBundleXmlApplicationContext appConfig = getAppConfig(skysailClientBundle);
            if (appConfig != null) {
                return appConfig;
            }
            try {
                //Thread.currentThread();
                Thread.sleep(waitingMilliSecons);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private OsgiBundleXmlApplicationContext getAppConfig(Bundle skysailClientBundle) {
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
