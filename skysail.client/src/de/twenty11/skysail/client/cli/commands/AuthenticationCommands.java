package de.twenty11.skysail.client.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationCommands implements CommandMarker {

	@Autowired
    private ApplicationContext applicationContext;
	
	@CliCommand(value = "login", help = "login -u username -p password")
	public String login(
			@CliOption(key = { "u" }, mandatory = true, help = "username") final String username,
			@CliOption(key = { "p" }, mandatory = true, help = "password") final String password
			) {

		System.out.println(applicationContext);
		return "hi";
	}
}
