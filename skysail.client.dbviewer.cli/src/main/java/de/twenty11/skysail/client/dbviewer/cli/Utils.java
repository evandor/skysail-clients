package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Context;

public class Utils {

    private Utils() {}
    
    static String getCurrentPath(Context ctx) {
        Object pathAsObject = ctx.getValue(Const.CURRENT_PATH);
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
    
    static String getPathArgument(Context ctx) {
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
    
    static boolean isConnected(Context ctx) {
        Object value = ctx.getValue(Const.HOST);
        if (value == null) {
            return false;
        } 
        if (!(value instanceof String)) {
            return false;
        }
        String strValue = (String) value;
        return strValue.trim().length() > 0;
    }
    
    static String getHost (Context ctx) {
        Object value = ctx.getValue(Const.HOST);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
    static String getUrl (Context ctx) {
        String path = Utils.getCurrentPath(ctx);
        if (path != null && path.length() > 0 && (path.charAt(0) == '/')) {
            return Utils.getHost(ctx) + path.substring(1,path.length());
        }
        return Utils.getHost(ctx) + path;
    }

}
