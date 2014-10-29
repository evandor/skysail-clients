package de.twenty11.skysail.client.cli.commands.test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

public class HeaderCommandsTest {

    private static JLineShellComponent shell;

    @BeforeClass
    public static void startUp() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        shell = bootstrap.getJLineShellComponent();
    }

    @AfterClass
    public static void shutdown() {
        shell.stop();
    }

    public static JLineShellComponent getShell() {
        return shell;
    }

    @Test
    public void dateTest() {
        CommandResult cr = getShell().executeCommand("setHeader --Accept text/html");
        assertThat(cr.getResult().toString(), containsString("[Accept: text/html]"));
    }
}
