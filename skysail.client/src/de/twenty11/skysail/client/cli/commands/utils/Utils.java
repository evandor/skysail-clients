package de.twenty11.skysail.client.cli.commands.utils;

import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.Const;


public class Utils {

    private Utils() {}
    
    public static String getPathArgument(Context ctx) {
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
    
    public static boolean isConnected(Context ctx) {
        Object value = ctx.getValue(Const.SERVER);
        if (value == null) {
            return false;
        } 
        if (!(value instanceof String)) {
            return false;
        }
        String strValue = (String) value;
        return strValue.trim().length() > 0;
    }
    
    public static String getServer (Context ctx) {
        Object value = ctx.getValue(Const.SERVER);
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }
    
   

}
