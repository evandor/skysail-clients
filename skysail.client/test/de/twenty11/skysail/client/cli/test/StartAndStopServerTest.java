package de.twenty11.skysail.client.cli.test;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.test.MockContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.twenty11.skysail.client.cli.commands.GetCommand;
import de.twenty11.skysail.client.cli.commands.PwdCommand;
import de.twenty11.skysail.client.cli.commands.SetServerCommand;

public class StartAndStopServerTest {

	Context ctx = MockContext.createInstance();

	Command setServerCommand = new SetServerCommand();
	Command getCommand = new GetCommand();
	Command pwdCommand = new PwdCommand();

	@BeforeClass
	public static void init() throws Exception {
		 SkysailServerLauncher.start(new String[0]);
	}

	@AfterClass
	public static void shutdown() throws InterruptedException {
		SkysailServerLauncher.getRunnable().terminate();
       // SkysailServerLauncher.getServerThread().join();
	}

	@Test
	public void startPage_for_unauthorized_user_has_no_links_to_secured_content() throws Exception {
		setServer("http://localhost:2016");
		String get = get();
		System.out.println(get);
	}

	private String get() {
		return (String)getCommand.execute(ctx);
	}

	private void setServer(String path) {
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS,path);
		setServerCommand.execute(ctx);
	}
}
