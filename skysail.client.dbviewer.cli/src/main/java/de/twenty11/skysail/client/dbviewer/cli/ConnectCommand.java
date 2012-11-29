package de.twenty11.skysail.client.dbviewer.cli;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.core.ShellException;

import de.twenty11.skysail.client.dbviewer.cli.Management.VmInfo;

public class ConnectCommand implements Command{
    public static final String CMD_NAME = "connect";
    public static final String NAMESPACE = "jmx";
    public static final String KEY_ARGS_HOST = "host";
    public static final String KEY_ARGS_UNAME = "user";
    public static final String KEY_ARGS_PWD = "password";
    public static final String KEY_ARGS_PID = "pid";
    
    private Command.Descriptor descriptor = null;
    
    public Descriptor getDescriptor() {
        return (descriptor != null ) ? descriptor : (
            descriptor = new Command.Descriptor() {

                public String getNamespace() {
                    return NAMESPACE;
                }

                public String getName() {
                    return CMD_NAME;
                }

                public String getDescription() {
                    return "Connects to local or remote JVM MBean server.";
                }

                public String getUsage() {
                    return "connect [pid:<ProcessId> host:<HostUrl> user:<UserName> password:<Password>]";
                }

                Map<String,String> args;
                public Map<String, String> getArguments() {
                    if(args != null) return args;
                    args = new LinkedHashMap<String,String>();
                    args.put(KEY_ARGS_PID + ":<ProcessId>", "Local process id of JVM to connect to (see 'ps' command)");
                    args.put(KEY_ARGS_HOST + ":<HostUrl>", "Connection URL as <hostname>:<port> or service:jmx:<protocol>:<url>");
                    args.put(KEY_ARGS_UNAME + ":<UserName>", "Username for connection");
                    args.put(KEY_ARGS_PWD + ":<Password>", "Password for connection");
                    return args;
                }
            }
        );
    }

    public Object execute(Context ctx) {
        invalidatePreviousConnection(ctx);
        Map<String,Object> argsMap = (Map<String,Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
        Map<Integer,VmInfo> localVms = (Map<Integer,VmInfo>) ctx.getValue(Management.KEY_VMINFO_MAP);
//        if(localVms == null){
//            mapVmInfoToContext(ctx);
//            localVms = (Map<Integer,VmInfo>) ctx.getValue(Management.KEY_VMINFO_MAP);
//        }
//        final IOConsole c = ctx.getIoConsole();
 
        MBeanServerConnection serverConnection = null;
        JMXConnector connector = null;
        
        String hostAddr = Management.getHostFromArgs(argsMap);
        String uname = (argsMap != null && argsMap.get(KEY_ARGS_UNAME) != null)
            ? (String)argsMap.get(KEY_ARGS_UNAME) : null;
        String pwd = (argsMap != null && argsMap.get(KEY_ARGS_PWD) != null)
            ? (String)argsMap.get(KEY_ARGS_PWD) : null;
        
        Integer pid = null;
        if(argsMap != null && argsMap.get(KEY_ARGS_PID) != null){
            try{
                // normalization due to json behavior
                Object val = argsMap.get(KEY_ARGS_PID);
                if(val instanceof String){
                    pid = Integer.valueOf((String)val);
                }
                if(val instanceof Double){
                    pid = ((Double)val).intValue();
                }
            }catch(Exception ex){
                throw new ShellException(String.format("Unable to determine "
                    + "value for pid [%s]: %s", argsMap.get(KEY_ARGS_PID), ex.getMessage()));
            }
        }
        
        // extract JMX URL.
//        JMXServiceURL jmxUrl = null;
//        try{
//            jmxUrl = Management.getJmxUrlFrom("localhost");
//
//            if (pid != null) {
//                try{
//                    VmInfo info = getVmInfoFromMap(pid, localVms);
//                    if (info != null) {
//                        hostAddr = info.getAddress();
//                        jmxUrl = Management.getJmxUrlFrom(hostAddr);
//                    }
//                    
//                    // if vminfo not cached already, try to find and connect
//                    else{
//                        
//                    }
//                }catch(Exception ex){
//                    throw new ShellException (String.format("Unable to understand "
//                        + "pid value: [%s]%", pid));
//                }
//            }
//            if (hostAddr != null){
//                jmxUrl = Management.getJmxUrlFrom(hostAddr);
//            }
//                        
//            ctx.putValue(Management.KEY_JMX_URL, jmxUrl);
//            
//        }catch(Exception ex){
//            throw new ShellException(String.format("%nConnection URL "
//                + "seems to be invalid: %s", ex.getMessage()));
//        }
        
        // add credentials info
        Map<String, String[]> env = null;
        if(uname != null && pwd != null){
            env = new HashMap<String,String[]>();
            env.put(JMXConnector.CREDENTIALS, new String[]{uname, pwd});
        }
        
        // connect
        try{
            // if pid nor hostaddr provided, use platform/local mbean server
            if(pid == null && hostAddr.equals(Management.VALUE_LOCALHOST)){
                connector = null;
                serverConnection = ManagementFactory.getPlatformMBeanServer();
                //c.writeOutput(String.format("%nConnected to internal server."));
            }else{
//                connector = JMXConnectorFactory.connect(jmxUrl, env);
//                serverConnection = connector.getMBeanServerConnection();
//                c.writeOutput(String.format("%nConnected to server (%s).", connector.getConnectionId()));
            }
            //c.writeOutput(String.format("%n%d MBean(s) registered with server.%n%n", serverConnection.getMBeanCount()));
            ctx.putValue(Management.KEY_JMX_CONNECTOR, connector);
            ctx.putValue(Management.KEY_JMX_MBEANSERVER, serverConnection);

        }catch(Exception ex){
            throw new ShellException(String.format("Unable to connect to"
                    + " MBeanServer: %s", ex.getMessage()));
        }
        return null;
    }

    public void plug(Context plug) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
//    private void mapVmInfoToContext (Context ctx){
//        try {
//            Map<Integer,VmInfo> vmMap = Management.mapVmInfo(Management.VALUE_LOCALHOST);
//            ctx.putValue(Management.KEY_VMINFO_MAP, vmMap);
//        } catch (Exception ex) {
//            ctx.putValue(Management.KEY_VMINFO_MAP, null);
//        }
//    }
    
    private void invalidatePreviousConnection(Context ctx){
        JMXConnector connector = (JMXConnector) ctx.getValue(Management.KEY_JMX_CONNECTOR);
        if(connector != null){
            try {
                connector.close();
            } catch (IOException ex) {
                throw new ShellException(String.format("Unable to close MBean "
                        + "server connection: %s", ex.getMessage()));
            }
        }
        // nullify references
        ctx.putValue(Management.KEY_JMX_URL, null);
        ctx.putValue(Management.KEY_JMX_CONNECTOR, null);
        ctx.putValue(Management.KEY_JMX_MBEANSERVER, null);
    }
    
    private VmInfo getVmInfoFromMap(Integer key, Map<Integer,VmInfo> jvmMap){
        if(jvmMap == null){
            return null;
        }
        return jvmMap.get(key);
    }
}