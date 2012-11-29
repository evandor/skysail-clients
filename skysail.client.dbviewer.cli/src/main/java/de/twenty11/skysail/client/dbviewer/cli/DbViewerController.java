package de.twenty11.skysail.client.dbviewer.cli;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.core.AnInputController;
import org.clamshellcli.core.ShellException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.management.ObjectInstance;

/**
* This is an implementation of the InputController for the JMX CLI.
* This implementation parses command-line input from Context(KEY_INPUT_LINE)
* value. It parses the input using JSon notation. The command-line input is
* expected to be of the form:
*
* <code>cmd param0:value para1:value1 ... paramN:valueN</code>
*
* The controller parses the input line and dispatches the parsed command and
* params to a Command instance on the classpath.
*
* @author vladimir.vivien
*/
public class DbViewerController extends AnInputController {
    private static final String JMX_NAMESPACE = "jmx";
    private Map<String, Command> commands;
    private Gson gson;
    
    @Override
    public boolean handle(Context ctx) {
        String cmdLine = (String)ctx.getValue(Context.KEY_COMMAND_LINE_INPUT);
        boolean handled = false;
               
        if(cmdLine != null && !cmdLine.trim().isEmpty()){
            String[] tokens = cmdLine.split("\\s+");
            String cmdName = tokens[0];
            Map<String,Object> argsMap = null;
            
            // if there are arguments
            if(tokens.length > 1 ){
                String argsString = convertParamsToString(Arrays.copyOfRange(tokens, 1, tokens.length));
                String argsJson = "{" + argsString + "}";
                try{
                    Type mapType = new TypeToken<Map<String,Object>>(){}.getType();
                    argsMap = gson.fromJson(argsJson, mapType);
                }catch(Exception ex){
                    ctx.getIoConsole().writeOutput(
                        String.format("%nUnable to parse command parameters [%s]: "
                            + " %s.%n%n", argsJson, ex.getMessage()));
                    return true;
                }
            }
            
            // launch command
            Command cmd = null;
            if(commands != null && (cmd = commands.get(cmdName)) != null){
                ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
                try{
                    cmd.execute(ctx);
                }catch(ShellException se){
                    printException (se, ctx);
                }
                handled = true;
            }else{
                handled = false;
            }
            
        }
        
        return handled;
    }

    @Override
    public void plug(Context plug) {
        super.plug(plug);
        gson = new Gson();
        
        List<Command> jmxCommands = plug.getCommandsByNamespace(JMX_NAMESPACE);
        if(jmxCommands.size() > 0){
            commands = plug.mapCommands(jmxCommands);
            Set<String> cmdHints = new TreeSet<String>();
            // plug each Command instance and collect input hints
            for(Command cmd : jmxCommands){
                cmd.plug(plug);
                cmdHints.addAll(collectInputHints(cmd));
            }
            // setup a map for cached jmx objects
            plug.putValue(Management.KEY_MBEANS_MAP, new HashMap<String,ObjectInstance>());
 
            
            // save expected command input hints
            setExpectedInputs(cmdHints.toArray(new String[0]));
            
        }else{
            plug.getIoConsole().writeOutput(
                String.format("%nNo commands were found for input controller"
                    + " [%s].%n%n", this.getClass().getName()));
        }
    }
    
    private void printException(ShellException ex, Context ctx){
        ctx.getIoConsole().writeOutput(
            String.format("%n%s%n%n", ex.getMessage())
        );
    }

    
    private String convertParamsToString(String[] params){
        StringBuffer buff = new StringBuffer();
        for(int i = 0; i < params.length; i++){
            buff.append(params[i].trim());
            if(i < (params.length-1)){
                buff.append(",");
            }
        }
        return buff.toString();
    }
}