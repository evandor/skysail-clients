//package de.twenty11.skysail.client.dbviewer.cli;
//
//import java.util.HashMap;
//
//import org.clamshellcli.api.Command;
//import org.clamshellcli.api.Context;
//import org.clamshellcli.test.MockContext;
//import org.junit.Before;
//import org.junit.Test;
//
//public class ShowConnectionsCommandTest {
//	private MockContext ctx;
//	private Command cmd;
//	private HashMap<String, String> argsMap;
//
//	@Before
//	public void setUp() {
//		ctx = MockContext.createInstance();
//		cmd = new ShowConnectionsCommand();
//		argsMap = new HashMap<String, String>();
//	}
//	
//	@Test
//	public void testme() {
//		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
//		Object execute = cmd.execute(ctx);
//	}
//}
