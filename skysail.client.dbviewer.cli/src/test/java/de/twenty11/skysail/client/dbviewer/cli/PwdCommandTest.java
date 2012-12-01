package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.clamshellcli.api.Command;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PwdCommandTest extends CommandTest {

    private Command cmd;

    @Before
    public void setUp() {
        resetContext();
        cmd = new PwdCommand();
    }

    @Test
    // call of "pwd"
    public void pwd_gives_null_path_the_first_time_called() {
        setHost("http://localhost");
        connect(null);
        String currentPath = (String) cmd.execute(ctx);
        assertThat(currentPath, is(equalTo("/")));
    }


}
