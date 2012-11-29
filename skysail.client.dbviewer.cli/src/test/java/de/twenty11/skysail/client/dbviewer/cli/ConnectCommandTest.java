package de.twenty11.skysail.client.dbviewer.cli;

import java.util.HashMap;
import java.util.Map;

import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;
import org.junit.Before;
import org.junit.Test;

public class ConnectCommandTest {
	
	private MockContext ctx;
	private ConnectCommand cmd;
	private HashMap<String, String> argsMap;

	@Before
	public void setUp() {
		ctx = MockContext.createInstance();
		cmd = new ConnectCommand();
		argsMap = new HashMap<String, String>();
	}

	@Test
	public void testExecuteWithDefaultHostArgs() throws Exception {
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
		cmd.execute(ctx);
		Object value = ctx.getValue(Management.KEY_JMX_URL);
	}

	@Test
	public void testExecuteWithHostArgs() throws Exception {
//		JmxAgent agent = TestUtils.startNewJmxAgent(1999);
//
//		argsMap.put(Management.KEY_ARGS_HOST, "localhost:1999");
//
//		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
//		cmd.execute(ctx);
//
//		JMXServiceURL url = (JMXServiceURL) ctx
//				.getValue(Management.KEY_JMX_URL);
//		JMXConnector c = (JMXConnector) ctx
//				.getValue(Management.KEY_JMX_CONNECTOR);
//		MBeanServerConnection cn = (MBeanServerConnection) ctx
//				.getValue(Management.KEY_JMX_MBEANSERVER);
//
//		assert url != null;
//		assert c != null;
//		assert cn != null;
//
//		agent.stop();
	}

	@Test
	public void testExecuteWithPidArgs() throws Exception {
//		JmxAgent agent = TestUtils.startNewJmxAgent(1999);
//
//		Map<Integer, VmInfo> vms = Management.mapVmInfo("localhost");
//		Integer vmId = vms.keySet().iterator().next();
//		argsMap.put(Management.KEY_ARGS_PID, vmId.toString());
//
//		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
//		cmd.execute(ctx);
//
//		JMXServiceURL url = (JMXServiceURL) ctx
//				.getValue(Management.KEY_JMX_URL);
//		JMXConnector c = (JMXConnector) ctx
//				.getValue(Management.KEY_JMX_CONNECTOR);
//		MBeanServerConnection cn = (MBeanServerConnection) ctx
//				.getValue(Management.KEY_JMX_MBEANSERVER);
//
//		assert url != null;
//		assert c != null;
//		assert cn != null;
//
//		agent.stop();

	}
}
