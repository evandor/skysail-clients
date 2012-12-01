package de.twenty11.skysail.client.dbviewer.cli;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

public class ChangePathCommand implements Command {

    private static final String NAMESPACE = "syscmd";
    private static final String ACTION_NAME = "cd";
    public static final String KEY_ARGS_PATH = "path";
    public static final String CURRENT_PATH = "currentPath";

    @Override
    public Descriptor getDescriptor() {
        return new Command.Descriptor() {

            Map<String, String> args;

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
                if (args != null) {
                    return args;
                }
                args = new LinkedHashMap<String, String>();
                args.put(KEY_ARGS_PATH + ":<Path>", "A remote path to change to");
                return args;
            }
        };

    }

    @Override
    public void plug(Context arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public Object execute(Context ctx) {
        String currentPath = getCurrentPath(ctx);
        String pathArgument = getPathArgument(ctx);
        
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
        ctx.putValue(CURRENT_PATH, currentPath);

        return currentPath;
    }

    private String getPathArgument(Context ctx) {
        String pathArgument = null;
        Object tmp = ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
        if (tmp instanceof String) {
            pathArgument = (String)tmp;
        } else if (tmp instanceof String[]) {
            String[] argsMap = (String[]) tmp;
            pathArgument = (argsMap != null && argsMap.length > 0) ? argsMap[0] : null;
        }
        return pathArgument;
    }

    private String getCurrentPath(Context ctx) {
        Object pathAsObject = ctx.getValue(CURRENT_PATH);
        if (pathAsObject == null) {
            return "/";
        } else {
            String pathAsString = (String) pathAsObject;
            if ("".equals(pathAsString.trim())) {
                return "/";
            } else {
                return pathAsString.trim();
            }
        }
    }

}
