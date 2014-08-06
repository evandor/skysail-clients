package de.twenty11.skysail.client.cli.commands;

import java.util.Map;

import org.apache.http.HttpResponse;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

public class LoginCommand extends AbstractCommand {

	@Override
	public HttpResponse execute(Context ctx) {
		@SuppressWarnings("unchecked")
		Map<String, Object> argsMap = (Map<String, Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
		String username = (String) argsMap.get("username");
		String password = (String) argsMap.get("password");
		
		String originalPath = CtxUtils.getCurrentPath(ctx);
		
		CtxUtils.setCurrentPath(ctx, "/_login");
		
		String url = CtxUtils.getUrl(ctx) ;
		HttpResponse response = HttpUtils.post(url, username, password);
		ConsoleUtils.writeStatus(ctx, response);
		ConsoleUtils.writeHeader(ctx, response);
		ConsoleUtils.writeBody(ctx, response);

		
		CtxUtils.setCurrentPath(ctx, originalPath);
		return response;
	}

}
