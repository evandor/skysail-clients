package de.twenty11.skysail.client.cli.commands;

import org.apache.http.HttpResponse;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.ConsoleUtils;
import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.HttpUtils;
import de.twenty11.skysail.client.cli.commands.utils.LinkUtils;

public class GetCommand implements Command {

	protected static final String ACTION_NAME = "get";

	private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME,
			"get",
			"executes http GET request on current path (see command 'pwd')");

	@Override
	public HttpResponse execute(Context ctx) {
		
		String arguments[] = ((String) ctx.getValue(Context.KEY_COMMAND_LINE_INPUT)).split(" ");
		if (arguments.length == 1) {
			return get(ctx);
		}
		if (arguments.length == 2) {
			return getLinks(ctx, arguments);
		}
		return null;
		
	}

	private HttpResponse getLinks(Context ctx, String[] arguments) {
		int linkNumber = Integer.parseInt(arguments[1]);
		String linkUri = LinkUtils.getLinkUri(ctx, linkNumber);
		CtxUtils.setCurrentPath(ctx, linkUri);
		//new ChangePathCommand().execute(ctx);
		ctx.putValue(Context.KEY_COMMAND_LINE_INPUT, "get");
		execute(ctx);
		return null;
	}

	private HttpResponse get(Context ctx) {
		String url = CtxUtils.getUrl(ctx) + "?media=json";
		ConsoleUtils.write(ctx, "> GET '" + url +"'\n");
		
		HttpResponse response = HttpUtils.get(url);
		ConsoleUtils.writeStatus(ctx, response);
		ConsoleUtils.writeHeader(ctx, response);
		ConsoleUtils.writeBody(ctx, response);
		
		LinkUtils.setCurrentLinks(ctx, response);
		
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
