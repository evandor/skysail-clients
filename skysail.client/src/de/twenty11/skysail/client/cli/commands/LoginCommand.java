package de.twenty11.skysail.client.cli.commands;

import org.apache.http.HttpResponse;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

public class LoginCommand extends AbstractCommand {

	private static final String LOGIN_CMD = "login";

	public LoginCommand() {
		commandDescriptor = new HttpCommandDescriptor(LOGIN_CMD,
				"login <username> <password>", "authenticate the provided user");
	}

	@Override
	public HttpResponse doExecute(Context ctx) {
		String arguments[] = ((String) ctx.getValue(Context.KEY_COMMAND_LINE_INPUT)).split(" ");
		String username = arguments[1].trim();
		String password = arguments[2].trim();

		String originalPath = CtxUtils.getCurrentPath(ctx);

		CtxUtils.setCurrentPath(ctx, "/_login");

		String url = CtxUtils.getUrl(ctx);
		HttpResponse response = HttpUtils.post(url, username, password);
		ConsoleUtils.writeStatus(ctx, response);
		ConsoleUtils.writeHeader(ctx, response);
		ConsoleUtils.writeBody(ctx, response);

		CtxUtils.setCurrentPath(ctx, originalPath);
		return response;
	}

}
