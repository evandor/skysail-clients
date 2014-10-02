//package de.twenty11.skysail.client.cli.test;
//
//import java.net.URL;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.osgi.framework.Bundle;
//import org.osgi.framework.FrameworkUtil;
//import org.springframework.osgi.context.support.OsgiBundleXmlApplicationContext;
//import org.springframework.shell.core.JLineShellComponent;
//
//import com.tngtech.jgiven.junit.ScenarioTest;
//
//public class MyShinyJGivenTest extends ScenarioTest<GivenSomeStage<?>, WhenSomeAction<?>, ThenSomeOutcome<?>> {
//
//    private static JLineShellComponent shell;
//
//    @BeforeClass
//    public static void init() {
//        
//        // the derby database gets deleted with gradle clean
//
//        try {
//            // Not sure which event I should wait for here, but need to wait
//            // nevertheless or the test will fail
//            // as the database is not created in time...
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
//        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");
//
//        OsgiBundleXmlApplicationContext appConfig = getAppContextOnceAvailable(skysailClientBundle, 500, 5);
//        MyBootstrap bootstrap = new MyBootstrap(appConfig, new String[0],
//                new String[] { shellPluginUrl.toExternalForm() });
//
//        Bundle[] bundles = skysailClientBundle.getBundleContext().getBundles();
//        for (Bundle bundle : bundles) {
//            System.out.println("running: " + bundle.getSymbolicName() + " '" + bundle.getVersion() + "': "
//                    + bundle.getState());
//        }
//
//        shell = bootstrap.getJLineShellComponent();
//    }
//    
//    private static OsgiBundleXmlApplicationContext getAppContextOnceAvailable(Bundle skysailClientBundle,
//            int waitingMilliSecons, int retries) {
//        for (int i = 0; i < retries; i++) {
//            //logger.info("waiting for appContext to become available #" + i);
//            OsgiBundleXmlApplicationContext appConfig = TestHelper.getAppConfig(skysailClientBundle);
//            if (appConfig != null) {
//              //  logger.info("found appContext...");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return appConfig;
//            }
//            try {
//                Thread.sleep(waitingMilliSecons);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    
//    @Test
//    public void something_should_happen() {
//        given().izzy_is_logged_in();
//        when().some_action();
//        then().some_outcome();
//    }
//    
//}