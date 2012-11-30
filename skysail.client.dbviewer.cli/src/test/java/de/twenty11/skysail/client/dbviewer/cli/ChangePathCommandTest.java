package de.twenty11.skysail.client.dbviewer.cli;

import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.core.ShellException;
import org.clamshellcli.test.MockContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ChangePathCommandTest extends CommandTest {

	private ChangePathCommand cmd;

	public ChangePathCommandTest() {
		cmd = new ChangePathCommand();
		ctx = MockContext.createInstance();
		cmd.plug(ctx);
	}

	@Before
	public void setUp() {
	}

	@Test
	public void should_xxx_with_no_parameters_provided() throws Exception {
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, null);
		try{
            cmd.execute(ctx);
        }catch(ShellException ex){
            Assert.fail();
        }
	}

	@Test
	// call of "cd mypath"
	public void sets_path_to_provided_argument_the_first_time_called() {
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "mypath");
		cmd.execute(ctx);
		assertThat(getCurrentPath(), is(equalTo("mypath")));
	}

	@Test
	// "cd parent"
	// "cd mypath"
	public void adds_provided_path_to_current_one() {
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "parent");
		cmd.execute(ctx);
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "mypath");
		cmd.execute(ctx);
		assertThat(getCurrentPath(), is(equalTo("parent/mypath")));
	}

	@Test
	// "cd mypath"
	// "cd ."
	public void one_dot_does_not_change_the_current_path() {
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "parent");
		cmd.execute(ctx);
		setArgument(ChangePathCommand.KEY_ARGS_PATH, ".");
		cmd.execute(ctx);
		assertThat(getCurrentPath(), is(equalTo("parent")));
	}

	@Test
	// "cd parent"
	// "cd .."
	public void two_dots_goes_up_in_current_path() {
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "parent");
		cmd.execute(ctx);
		setArgument(ChangePathCommand.KEY_ARGS_PATH, "..");
		cmd.execute(ctx);
		assertThat(getCurrentPath(), is(equalTo("")));
	}

	private String getCurrentPath() {
		return (String) ctx.getValue(ChangePathCommand.CURRENT_PATH);
	}

}
