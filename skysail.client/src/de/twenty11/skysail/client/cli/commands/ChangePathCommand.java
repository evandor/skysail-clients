package de.twenty11.skysail.client.cli.commands;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class ChangePathCommand implements Command {

    private static final String ACTION_NAME = "cd";

    private Descriptor descriptor = new HttpCommandDescriptor(ACTION_NAME, "cd <path>", "change path") {
        @Override
        public Map<String, String> getArguments() {
            Map<String, String> args = new LinkedHashMap<String, String>();
            args.put(Const.KEY_ARGS_PATH + ":<Path>", "A remote path to change to");
            return args;
        }
    };

    @Override
    public Object execute(Context ctx) {
        IOConsole console = ctx.getIoConsole();
//        if (!Utils.isConnected(ctx)) {
//            console.writeOutput("please connect first before using this command\n");
//            return "not connected";
//        }

        String msg = "";
        String currentPath = Utils.getCurrentPath(ctx);
        String pathArgument = Utils.getPathArgument(ctx);
        
        if (pathArgument == null || pathArgument.trim().length() == 0) {
            currentPath = "/";
        } else if (pathArgument.equals(".")) {
            return currentPath;
        } else if (pathArgument.equals("..")) {
            List<String> partsOfPath = Arrays.asList(currentPath.split("/"));
            if (partsOfPath.size() == 0) {
                return "/";
            } else if (partsOfPath.size() == 2) {
                currentPath = "/" + partsOfPath.get(0);
            } else {
                List<String> firstParts = partsOfPath.subList(0, partsOfPath.size() - 1);
                currentPath = StringUtils.join(firstParts, "/");
            }
        } else if (!"/".equals(currentPath)) {
            currentPath = currentPath + "/" + pathArgument;
        } else {
            currentPath = "/" + pathArgument;
        }
        ctx.putValue(Const.CURRENT_PATH, currentPath);

        return msg;
    }

    @Override
    public Descriptor getDescriptor() {
        return this.descriptor;
    }

    @Override
    public void plug(Context arg0) {}

}
