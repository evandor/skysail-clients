package de.twenty11.skysail.client.dbviewer.cli;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.clamshellcli.api.Command.Descriptor;
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

    @Test
    public void should_get_error_message_when_not_connected() throws Exception {
        setArgument("");
        String msg = (String)cmd.execute(ctx);
        assertThat(msg, is(equalTo("not connected")));
    }
    
    @Test
    public void should_set_path_to_root_when_called_with_empty_parameters() throws Exception {
        setHost("http://localhost");
        connect(null);
        
        setArgument("");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/")));
    }

    @Test
    // call of "cd mypath"
    public void sets_path_to_provided_argument_the_first_time_called() {
        setHost("http://localhost");
        connect(null);
        setArgument("mypath");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/mypath")));
    }

    @Test
    public void adds_provided_path_to_current_one() {
        setHost("http://localhost");
        connect(null);
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("mypath");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent/mypath")));
    }

    @Test
    public void one_dot_does_not_change_the_current_path() {
        setHost("http://localhost");
        connect(null);
        setArgument("parent");
        cmd.execute(ctx);
        setArgument(".");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent")));
    }

    @Test
    public void two_dots_goes_up_in_current_path() {
        setHost("http://localhost");
        connect(null);
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("..");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/")));
    }

    @Test
    public void two_dots_goes_up_in_current_path_with_depth_more_than_one() {
        setHost("http://localhost");
        connect(null);
        setArgument("parent");
        cmd.execute(ctx);
        setArgument("sub");
        cmd.execute(ctx);
        setArgument("..");
        cmd.execute(ctx);
        assertThat(getCurrentPath(), is(equalTo("/parent")));
    }

    @Test
    public void descriptor_provideds_cd_command_info() {
        Descriptor descriptor = cmd.getDescriptor();
        assertThat(descriptor.getUsage(), is(equalTo("cd <path>")));
        assertThat(descriptor.getArguments().containsKey(Const.KEY_ARGS_PATH + ":<Path>"), is(true));
    }
    
    private String getCurrentPath() {
        return (String) ctx.getValue(Const.CURRENT_PATH);
    }

}
