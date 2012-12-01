package de.twenty11.skysail.client.dbviewer.cli;

import java.util.Collections;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.dbviewer.cli.internal.Const;
import de.twenty11.skysail.client.dbviewer.cli.internal.Utils;

public class SetHostCommand implements Command {

    protected static final String ACTION_NAME = "setHost";
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
                return "setHost <hostname>, e.g. setHost http://localhost:8888/";
            }

            public Map<String, String> getArguments() {
                return Collections.emptyMap();
            }
        });
    }

    @Override
    public Object execute(Context ctx) {
        String pathArgument = Utils.getPathArgument(ctx);
        ctx.putValue(Const.HOST, pathArgument);
        return null;
    }

    @Override
    public void plug(Context arg0) {
        // TODO Auto-generated method stub

    }

}
