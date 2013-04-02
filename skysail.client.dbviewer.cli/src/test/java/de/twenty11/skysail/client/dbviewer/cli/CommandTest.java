package de.twenty11.skysail.client.dbviewer.cli;

import java.util.HashMap;
import java.util.Map;

import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;

import de.twenty11.skysail.client.dbviewer.cli.internal.Const;

public class CommandTest {

    protected MockContext ctx = MockContext.createInstance();
    protected Map<String, String> argsMap = new HashMap<String, String>();

    protected void resetContext() {
        ctx.removeValue(Const.CURRENT_PATH);
    }

    protected void setArgument(String str) {
        ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, str);
    }

    protected void reset(MockContext ctx) {
        ctx.removeValue(Context.KEY_COMMAND_LINE_ARGS);
        ctx.removeValue(Const.CURRENT_PATH);
        ctx.removeValue(Const.SERVER);
    }

    protected String setHost(String host) {
        SetServerCommand shc = new SetServerCommand();
        setArgument(host);
        return (String) shc.execute(ctx);
    }

    protected String connect(String host) {
        ConnectCommand cc = new ConnectCommand();
        setArgument(host);
        return (String) cc.execute(ctx);
    }

    protected String cd(String path) {
        ChangePathCommand cpc = new ChangePathCommand();
        setArgument(path);
        return (String) cpc.execute(ctx);
    }
    
}
