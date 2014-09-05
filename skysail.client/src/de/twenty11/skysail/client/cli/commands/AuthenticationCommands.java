package de.twenty11.skysail.client.cli.commands;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
import de.twenty11.skysail.client.cli.utils.OutputUtils;

@Component
public class AuthenticationCommands implements CommandMarker {

	@Autowired
    private Context context;
	
	@CliCommand(value = "login", help = "login -u username -p password")
	public String login(
			@CliOption(key = { "u" }, mandatory = true, help = "username") final String username,
			@CliOption(key = { "p" }, mandatory = true, help = "password") final String password,
			@CliOption(key = { "path" }, mandatory = false, help = "path where to login", unspecifiedDefaultValue= "/_login", specifiedDefaultValue="/_login") final String loginPath
			) {

		String originalPath = context.getPath();

		context.setPath(loginPath);
		
		HttpResponse response = HttpUtils.post(context.getCurrentUrl(), username, password);
		
		StringBuilder sb = new StringBuilder();
		sb.append(OutputUtils.printResponseHeader(response));
		sb.append(OutputUtils.printBody(response));

		context.setPath(originalPath);
		return sb.toString();
	}
}
