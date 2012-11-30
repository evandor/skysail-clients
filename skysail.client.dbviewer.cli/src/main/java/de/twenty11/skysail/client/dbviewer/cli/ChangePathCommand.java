package de.twenty11.skysail.client.dbviewer.cli;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Configurator;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;

public class ChangePathCommand implements Command {

    private static final String NAMESPACE = "syscmd";
    private static final String ACTION_NAME = "cd";
    public static final String KEY_ARGS_PATH = "path";
    public static final String CURRENT_PATH = "currentPath";

    @Override
    public Descriptor getDescriptor() {
        return new Command.Descriptor() {
            
            Map<String,String> args;
            
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
                if(args != null) {
                    return args;
                }
                args = new LinkedHashMap<String,String>();
                args.put(KEY_ARGS_PATH +":<Path>", "A remote path to change to");
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
        IOConsole console = ctx.getIoConsole();
        //console.writeOutput(String.format("%n%s%n%n",new Date().toString()));
        
        Map<String,Object> argsMap = (Map<String,Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
        Object pathParam = (argsMap != null) ? argsMap.get(KEY_ARGS_PATH) : null;
        String pathIdentifier ;
        if (pathParam instanceof String) {
            pathIdentifier = (String) pathParam;
            
        } else {
            console.writeOutput("pathParam was of type " + pathParam.getClass().getCanonicalName());
            return null;
        }
        
        if(pathIdentifier != null && !pathIdentifier.isEmpty()){
            console.writeOutput(pathIdentifier + Configurator.VALUE_LINE_SEP);
        }
        //Map<String,Object> argsMap = (Map<String,Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
        //String pathIdentifier = (argsMap  != null) ? (String)argsMap.get(KEY_ARGS_PATH) : null;

        String path = (String) ctx.getValue(CURRENT_PATH);
        if (pathIdentifier.equals(".")) {
            return null;
        } else if (pathIdentifier.equals("..")) {
            List<String> partsOfPath = Arrays.asList(path.split("/"));
            if (partsOfPath.size() == 0) {
                return null;
            } else {
                List<String> firstParts = partsOfPath.subList(0, partsOfPath.size()-1);
                path = StringUtils.join(firstParts, "/");
            }
        } else if (path != null) {
            path = path + "/" + pathIdentifier;
        } else {
            path = pathIdentifier;
        }
        ctx.putValue(CURRENT_PATH, path);
        return null;
    }


}
