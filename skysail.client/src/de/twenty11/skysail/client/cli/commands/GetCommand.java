package de.twenty11.skysail.client.cli.commands;

import org.apache.http.HttpResponse;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;

public class GetCommand implements Command {

	protected static final String ACTION_NAME = "get";

	private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME,
			"get",
			"executes http GET request on current path (see command 'pwd')");

	@Override
	public HttpResponse execute(Context ctx) {
		String url = CtxUtils.getUrl(ctx) + "?media=json";
		ConsoleUtils.write(ctx, "> GET '" + url +"'\n");
		
		HttpResponse response = HttpUtils.get(url);
		ConsoleUtils.writeStatus(ctx, response);
		ConsoleUtils.writeHeader(ctx, response);
		ConsoleUtils.writeBody(ctx, response);
		return response;
	}

	@Override
	public Descriptor getDescriptor() {
		return this.descriptor;
	}

	@Override
	public void plug(Context arg0) {
	}

}
