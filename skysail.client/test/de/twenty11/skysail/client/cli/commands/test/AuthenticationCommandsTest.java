package de.twenty11.skysail.client.cli.commands.test;

import java.text.ParseException;

import org.junit.Test;
import org.springframework.shell.core.CommandResult;

public class AuthenticationCommandsTest extends TestBase {

	@Test
	public void dateTest() throws ParseException {

		// Execute command
		CommandResult cr = getShell().executeCommand(
				"login --u admin --p skysail");

		cr.getResult().toString();

	}

}
