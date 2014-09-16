package de.twenty11.skysail.client.cli.test;

import java.net.URL;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

import de.twenty11.skysail.client.cli.commands.Context;
import de.twenty11.skysail.client.cli.commands.RestCommands;

public class TestBase {

    protected static JLineShellComponent shell;
    
    @Before
	public void startUp() throws Exception {
//        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
//        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");
//        
//        Bundle springContextBundle = FrameworkUtil.getBundle(org.springframework.context.ApplicationContext.class);
//		URL shemasUrl = springContextBundle.getEntry("META-INF/spring.schemas");
        
//        Bootstrap bootstrap = new Bootstrap(new String[0], new String[] {shellPluginUrl.toExternalForm(), shemasUrl.toExternalForm()}); 
////        ApplicationContext applicationContext = bootstrap.getApplicationContext();
////        ((GenericApplicationContext)applicationContext).getAutowireCapableBeanFactory().createBean(Context.class, AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, false);
////        ((GenericApplicationContext)applicationContext).getAutowireCapableBeanFactory().createBean(RestCommands.class, AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, true);
//        //System.out.println(applicationContext.getClass().getName());
//		shell = bootstrap.getJLineShellComponent();
	}

    @After
    public void shutdown() throws Exception {
        shell.stop();
    }

    public static JLineShellComponent getShell() {
        return shell;
    }

    protected CommandResult exec(String... command) {
        return Arrays.stream(command).map(cmd -> getShell().executeCommand(cmd)).reduce((cr1, cr2) -> cr2).get();
    }

}
