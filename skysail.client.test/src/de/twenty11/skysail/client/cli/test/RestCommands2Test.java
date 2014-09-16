package de.twenty11.skysail.client.cli.test;

import java.net.URL;

import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;

import de.twenty11.skysail.client.cli.commands.Context;
import de.twenty11.skysail.client.cli.commands.RestCommands;


public class RestCommands2Test extends TestBase {

    @Test
   // @Ignore // LargeTest
    public void testName2() throws Exception {
        Bundle skysailClientBundle = FrameworkUtil.getBundle(de.twenty11.skysail.client.SkysailClient.class);
        URL shellPluginUrl = skysailClientBundle.getEntry("META-INF/spring/spring-shell-plugin.xml");
        
      Bundle springContextBundle = FrameworkUtil.getBundle(org.springframework.context.ApplicationContext.class);
    URL shemasUrl = springContextBundle.getEntry("META-INF/spring.schemas");

       
        Bootstrap bootstrap = new Bootstrap(new String[0], new String[] {shellPluginUrl.toExternalForm()}); 
//      ApplicationContext applicationContext = bootstrap.getApplicationContext();
//      ((GenericApplicationContext)applicationContext).getAutowireCapableBeanFactory().createBean(Context.class, AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, false);
//      ((GenericApplicationContext)applicationContext).getAutowireCapableBeanFactory().createBean(RestCommands.class, AutowireCapableBeanFactory.AUTOWIRE_AUTODETECT, true);
      //System.out.println(applicationContext.getClass().getName());
      shell = bootstrap.getJLineShellComponent();
        CommandResult exec = exec("get", "get --uri dbc");
    }
}
