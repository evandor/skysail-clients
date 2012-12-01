package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;
import org.junit.Before;
import org.junit.Test;

public class ChangePathCommandTest extends CommandTest {

    private ChangePathCommand cmd;

    public ChangePathCommandTest() {
        cmd = new ChangePathCommand();
    }

    @Before
    public void setUp() {
        // needs to be done as we get same ctx Object everytime
        reset(ctx);
    }

    private void reset(MockContext ctx) {
        ctx.removeValue(Context.KEY_COMMAND_LINE_ARGS);
        ctx.removeValue(ChangePathCommand.CURRENT_PATH);
    }

    @Test
    public void should_set_path_to_root_when_called_with_empty_parameters() throws Exception {
        setArgument("");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/")));
    }

    @Test
    // call of "cd mypath"
    public void sets_path_to_provided_argument_the_first_time_called() {
        setArgument("mypath");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/mypath")));
    }

    @Test
    public void adds_provided_path_to_current_one() {
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("mypath");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent/mypath")));
    }

    @Test
    public void one_dot_does_not_change_the_current_path() {
        setArgument("parent");
        cmd.execute(ctx);
        setArgument(".");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent")));
    }

    @Test
    public void two_dots_goes_up_in_current_path() {
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("..");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/")));
    }

    @Test
    public void two_dots_goes_up_in_current_path_with_depth_more_than_one() {
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("sub");
        cmd.execute(ctx);
        setArgument("..");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent")));
    }

    private String getCurrentPath() {
        return (String) ctx.getValue(ChangePathCommand.CURRENT_PATH);
    }

}
