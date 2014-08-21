package de.twenty11.skysail.client.cli.commands;

import java.net.MalformedURLException;
import java.net.URL;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;
import de.twenty11.skysail.client.cli.commands.utils.Utils;

public class SetServerCommand extends AbstractCommand {

	private static final String ACTION_NAME = "setServer";

	public SetServerCommand() {
		commandDescriptor = new HttpCommandDescriptor(ACTION_NAME, "setServer", "sets server");
	}

	@Override
	public Object doExecute(Context ctx) {
		IOConsole console = ctx.getIoConsole();
        String pathArgument = Utils.getPathArgument(ctx);
        try {
            URL url = new URL(pathArgument);
            String serverUrl = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                serverUrl += ":" + url.getPort();
            }
            CtxUtils.setServer(ctx, serverUrl);
            console.writeOutput("'" + Const.SERVER + "' was set to '" + serverUrl + "'\n");
            return serverUrl;
        } catch (MalformedURLException e) {
            console.writeOutput("setServer argument '" + pathArgument + "' could not be parsed as URL: "
                    + e.getMessage());
            return null;
        }
	}

}
