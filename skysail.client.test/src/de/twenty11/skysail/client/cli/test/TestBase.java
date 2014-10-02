package de.twenty11.skysail.client.cli.test;

import java.net.URL;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

public class TestBase {

    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static JLineShellComponent shell;
    private static BundleContext bundleContext;

    @BeforeClass
    public static void init() {
        
        // the derby database gets deleted with gradle clean

        try {
            // Not sure which event I should wait for here, but need to wait
            // nevertheless or the test will fail
            // as the database is not created in time...
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");

        OsgiBundleXmlApplicationContext appConfig = getAppContextOnceAvailable(skysailClientBundle, 500, 5);
        MyBootstrap bootstrap = new MyBootstrap(appConfig, new String[0],
                new String[] { shellPluginUrl.toExternalForm() });

        Bundle[] bundles = skysailClientBundle.getBundleContext().getBundles();
        for (Bundle bundle : bundles) {
            System.out.println("running: " + bundle.getSymbolicName() + " '" + bundle.getVersion() + "': "
                    + bundle.getState());
        }

        shell = bootstrap.getJLineShellComponent();
    }

    @Before
    public void startUp() throws Exception {

    }

    @After
    public void shutdown() throws Exception {
        shell.stop();
    }

    public static JLineShellComponent getShell() {
        return shell;
    }

    protected CommandResult exec(String... command) {
        return Arrays.stream(command).map(cmd -> exe(cmd)).reduce((cr1, cr2) -> cr2).get();
    }

    private CommandResult exe(String cmd) {
        return getShell().executeCommand(cmd);
    }

    private static OsgiBundleXmlApplicationContext getAppContextOnceAvailable(Bundle skysailClientBundle,
            int waitingMilliSecons, int retries) {
        for (int i = 0; i < retries; i++) {
            logger.info("waiting for appContext to become available #" + i);
            OsgiBundleXmlApplicationContext appConfig = getAppConfig(skysailClientBundle);
            if (appConfig != null) {
                logger.info("found appContext...");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return appConfig;
            }
            try {
                // Thread.currentThread();
                Thread.sleep(waitingMilliSecons);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static OsgiBundleXmlApplicationContext getAppConfig(Bundle skysailClientBundle) {
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
