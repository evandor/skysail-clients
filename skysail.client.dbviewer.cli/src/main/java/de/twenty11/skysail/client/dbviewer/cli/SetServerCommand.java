package de.twenty11.skysail.client.dbviewer.cli;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

import de.twenty11.skysail.client.dbviewer.cli.internal.Const;
import de.twenty11.skysail.client.dbviewer.cli.internal.Utils;

public class SetServerCommand implements Command {

    protected static final String ACTION_NAME = "setServer";
    private Descriptor descriptor;

    public Descriptor getDescriptor() {
        return (descriptor != null) ? descriptor : (descriptor = new Command.Descriptor() {

            public String getNamespace() {
                return Const.NAMESPACE;
            }

            public String getName() {
                return ACTION_NAME;
            }

            public String getDescription() {
                return "sets the host";
            }

            public String getUsage() {
                return "setServer <serverUrl>, e.g. setServer http://localhost:8888";
            }

            public Map<String, String> getArguments() {
                return Collections.emptyMap();
            }
        });
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
    public void plug(Context arg0) {
    }

}
