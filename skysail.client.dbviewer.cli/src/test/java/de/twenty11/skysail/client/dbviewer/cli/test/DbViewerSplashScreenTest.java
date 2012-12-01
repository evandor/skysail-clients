package de.twenty11.skysail.client.dbviewer.cli.test;

import org.junit.Test;

import de.twenty11.skysail.client.dbviewer.cli.CommandTest;
import de.twenty11.skysail.client.dbviewer.cli.DbViewerSplashScreen;


public class DbViewerSplashScreenTest extends CommandTest {

    @Test
    public void testSlashScreen() throws Exception {
        DbViewerSplashScreen screen = new DbViewerSplashScreen();
        screen.render(ctx);
    }
}
