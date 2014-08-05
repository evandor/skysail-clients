package de.twenty11.skysail.client.cli.commands;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class PwdCommand implements Command {

    private static final String ACTION_NAME = "pwd";
    
    private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME, "pwd", "prompt current working directory");
    

    @Override
    public String execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
        String host = Utils.getServer(ctx);
        String path = Utils.getCurrentPath(ctx);
        String output = host + path;
		console.writeOutput(output);
        console.writeOutput("\n");
        return output;
    }

    @Override
    public void plug(Context arg0) {}

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

}
