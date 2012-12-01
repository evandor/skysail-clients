//package de.twenty11.skysail.client.dbviewer.cli;
//
//import java.lang.management.ManagementFactory;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.management.MBeanServerConnection;
//import javax.management.ObjectInstance;
//
//import org.clamshellcli.api.Context;
//
//public class TestUtils {
//    public static String MBEAN_NAME = "test.jmx:type=bean";
//    
//    public static void setupJmxConnection(Context ctx) throws Exception{
//        MBeanServerConnection conn = ManagementFactory.getPlatformMBeanServer();
//        assert conn != null;
//        ctx.putValue(Management.KEY_JMX_MBEANSERVER, conn);
//    }
//    
//    public static void setupDefaultMBeanInstance(Context ctx) throws Exception{
//        Map<String,ObjectInstance> beanMap = new HashMap<String,ObjectInstance>();
//        ctx.putValue(Management.KEY_MBEANS_MAP, beanMap);
////        Command cmd = new MBeanCommand();
////        cmd.plug(ctx);
////        
////        Map<String,Object> argsMap = (ctx.getValue(Context.KEY_COMMAND_LINE_ARGS) != null)
////                ? (Map<String,Object>) ctx.getValue(Context.KEY_COMMAND_LINE_ARGS)
////                : new HashMap<String,Object>();
////        argsMap.put(MBeanCommand.KEY_ARGS_BEAN, "java.lang:type=Runtime");
////        ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
////        cmd.execute(ctx);
////        
////        assert beanMap.get(Management.KEY_DEFAULT_MBEANS) != null;
//    }
//    
//    
//}
