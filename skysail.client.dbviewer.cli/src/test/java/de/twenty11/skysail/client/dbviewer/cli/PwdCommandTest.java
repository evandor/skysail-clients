package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.clamshellcli.api.Command;
import org.clamshellcli.test.MockContext;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PwdCommandTest extends CommandTest {

    private Command cmd;

    @Before
    public void setUp() {
        ctx = MockContext.createInstance();
        resetContext();
        cmd = new PwdCommand();
    }

    @Test
    // call of "pwd"
    public void pwd_gives_null_path_the_first_time_called() {
        //setArgument("");
        String currentPath = (String) cmd.execute(ctx);
        assertThat(currentPath, is(equalTo(null)));
    }

    @Test
    // call of "pwd"
    public void pwd_returns_current_path_after_setting_with_cd() {
        cd("path");
        //setArgument("");
        String currentPath = (String) cmd.execute(ctx);
        assertThat(currentPath, is(equalTo("path")));
    }

    private void cd(String path) {
        ChangePathCommand cpc = new ChangePathCommand();
        setArgument(ChangePathCommand.KEY_ARGS_PATH, path);
        cpc.execute(ctx);
    }


}
