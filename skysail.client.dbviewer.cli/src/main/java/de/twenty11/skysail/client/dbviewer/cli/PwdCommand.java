package de.twenty11.skysail.client.dbviewer.cli;

import java.util.Collections;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class PwdCommand implements Command {

    private static final String NAMESPACE = "syscmd";
    private static final String ACTION_NAME = "pwd";
    
    @Override
    public void plug(Context arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public Object execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
        String path = (String) ctx.getValue(ChangePathCommand.CURRENT_PATH);
        console.writeOutput(path == null ? "<null>" : path);
        console.writeOutput("\n");
        return path;
    }

    @Override
    public Descriptor getDescriptor() {
        return new Command.Descriptor() {
            @Override
            public String getNamespace() {
                return NAMESPACE;
            }

            @Override
            public String getName() {
                return ACTION_NAME;
            }

            @Override
            public String getDescription() {
                return "change path";
            }

            @Override
            public String getUsage() {
                return "cd path:<Path>";
            }

            @Override
            public Map<String, String> getArguments() {
                return Collections.emptyMap();
            }
        };
    }

}
