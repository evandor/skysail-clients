package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;

public class CommandTest {

    MockContext ctx;

    protected void resetContext() {
        ctx.removeValue(ChangePathCommand.CURRENT_PATH);
    }

    protected void setArgument(String str) {
        //argsMap.put(ChangePathCommand.KEY_ARGS_PATH, path);
        ctx.putValue(Context.KEY_COMMAND_LINE_INPUT, str);
    }

}
