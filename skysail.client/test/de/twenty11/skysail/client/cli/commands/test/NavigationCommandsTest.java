package de.twenty11.skysail.client.cli.commands.test;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.shell.core.CommandResult;


public class NavigationCommandsTest extends TestBase {

	@Before
	public void setUp ( ) {
		exec("cd");
	}
	
	@Test
	public void cd_without_arguments_sets_path_to_empty() throws Exception {
		assertPath(exec("cd"), "''");
	}
	
	@Test
	public void cd_adds_argument_to_path() throws Exception {
		assertPath(exec("cd test"), "'/test'");
	}
	
	@Test
	public void cd_without_arguments_sets_path_to_empty2() throws Exception {
		assertPath(exec("cd test", "cd"), "''");
	}
	
	@Test
	public void cd_sets_absolute_paths() throws Exception {
		assertPath(exec("cd /path"), "'/path'");
		assertPath(exec("cd /path/"), "'/path/'");
		assertPath(exec("cd /path/sub"), "'/path/sub'");
	}
	
	@Test
	public void testName() throws Exception {
		assertPath(exec("cd path", "cd .."), "''");
	}

	private void assertPath(CommandResult cmd, String result) {
		assertThat(cmd.getResult().toString(), containsString(result));
	}
}
