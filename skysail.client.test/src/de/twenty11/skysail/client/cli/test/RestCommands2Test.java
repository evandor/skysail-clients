package de.twenty11.skysail.client.cli.test;

import java.net.URL;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.springframework.context.ApplicationContext;
import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
import org.springframework.shell.core.CommandResult;

public class RestCommands2Test extends TestBase {

    @Test
    // @Ignore // LargeTest
    public void testName2() throws Exception {
        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");
//
//        Bundle springContextBundle = FrameworkUtil.getBundle(org.springframework.context.ApplicationContext.class);
//        URL shemasUrl = springContextBundle.getEntry("META-INF/spring.schemas");

        ServiceReference<ApplicationContext> appConfigServiceRef = skysailClientBundle.getBundleContext()
                .getServiceReference(org.springframework.context.ApplicationContext.class);
        OsgiBundleXmlApplicationContext appConfig = (OsgiBundleXmlApplicationContext)skysailClientBundle.getBundleContext().getService(appConfigServiceRef);
        MyBootstrap bootstrap = new MyBootstrap(appConfig, new String[0], new String[] {shellPluginUrl.toExternalForm()});
        shell = bootstrap.getJLineShellComponent();
        CommandResult exec = exec("get", "get --uri dbc");
    }
}
