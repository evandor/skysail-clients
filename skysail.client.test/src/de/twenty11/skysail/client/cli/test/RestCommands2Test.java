package de.twenty11.skysail.client.cli.test;

import org.junit.Test;
import org.springframework.shell.core.CommandResult;


public class RestCommands2Test extends TestBase {

    @Test
   // @Ignore // LargeTest
    public void testName2() throws Exception {
        CommandResult exec = exec("get", "get --uri dbc");
    }
}
