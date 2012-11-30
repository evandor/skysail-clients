package de.twenty11.skysail.client.dbviewer.cli;

import java.util.HashMap;
import java.util.Map;

import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;

public class CommandTest {

    protected MockContext ctx;
    protected Map<String, String> argsMap = new HashMap<String, String>();

    protected void resetContext() {
        ctx.removeValue(ChangePathCommand.CURRENT_PATH);
    }

    protected void setArgument(String key, String str) {
    	argsMap.put(key, str);
        ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
    	
        //argsMap.put(ChangePathCommand.KEY_ARGS_PATH, path);
        //ctx.putValue(Context.KEY_COMMAND_LINE_INPUT, str);
    }

}
