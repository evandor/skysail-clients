package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class PwdCommand implements Command {

    private static final String ACTION_NAME = "pwd";
    
    private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME, "pwd", "prompt current working directory");
    

    @Override
    public Object execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
        if (!Utils.isConnected(ctx)) {
            console.writeOutput("please connect first before using this command\n");
            return "not connected";
        }

        String host = Utils.getHost(ctx);
        String path = Utils.getCurrentPath(ctx);
        console.writeOutput(host + path);
        console.writeOutput("\n");
        return path;
    }

    @Override
    public void plug(Context arg0) {}

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

}
