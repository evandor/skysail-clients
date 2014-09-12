package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

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
			@CliOption(key = { "path" }, mandatory = false, help = "path where to login", unspecifiedDefaultValue = "/_login", specifiedDefaultValue = "/_login") final String loginPath) {

		String originalPath = context.getPath();

		context.setPath(loginPath);

		HttpResponse response = HttpUtils.postForLogin(context.getCurrentUrl(),
				username, password);

		// context.setRequestHeaders(Collections.emptyList());
		context.setResponseHeaders(response.getAllHeaders());
		try {
			context.setBody(EntityUtils.toString(response.getEntity()));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.setStatus(response.getStatusLine());

		StringBuilder sb = new StringBuilder();
		sb.append(OutputUtils.printResponseHeader(context));
		sb.append(OutputUtils.printBody(context));

		context.setPath(originalPath);
		return sb.toString();
	}

	@CliCommand(value = "logout", help = "logout current user")
	public String logout() {

		String originalPath = context.getPath();

		context.setPath("/_logout");

		HttpResponse response = HttpUtils.get(context);
		
		// context.setRequestHeaders(Collections.emptyList());
		context.setResponseHeaders(response.getAllHeaders());
		try {
			context.setBody(EntityUtils.toString(response.getEntity()));
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.setStatus(response.getStatusLine());

		StringBuilder sb = new StringBuilder();
		sb.append(OutputUtils.printResponseHeader(context));
		sb.append(OutputUtils.printBody(context));

		context.setPath(originalPath);
		return sb.toString();
	}
}
