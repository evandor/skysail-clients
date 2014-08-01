package de.twenty11.skysail.client.cli.commands;

import java.net.MalformedURLException;
import java.net.URL;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class SetServerCommand implements Command {

	@Override
	public void plug(Context arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object execute(Context ctx) {
		IOConsole console = ctx.getIoConsole();
        String pathArgument = Utils.getPathArgument(ctx);
        try {
            URL url = new URL(pathArgument);
            String serverUrl = url.getProtocol() + "://" + url.getHost();
            if (url.getPort() != -1) {
                serverUrl += ":" + url.getPort();
            }
            ctx.putValue(Const.SERVER, serverUrl);
            console.writeOutput("'" + Const.SERVER + "' was set to '" + serverUrl + "'\n\n");
            return serverUrl;
        } catch (MalformedURLException e) {
            console.writeOutput("setServer argument '" + pathArgument + "' could not be parsed as URL: "
                    + e.getMessage());
            return null;
        }
	}

	@Override
	public Descriptor getDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

}
