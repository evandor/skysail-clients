package de.twenty11.skysail.client.cli.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;
import org.clamshellcli.test.MockContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.ConnectCommand;
import de.twenty11.skysail.client.cli.commands.GetCommand;
import de.twenty11.skysail.client.cli.commands.PwdCommand;
import de.twenty11.skysail.client.cli.commands.Response;
import de.twenty11.skysail.client.cli.commands.SetServerCommand;

public class StartAndStopServerTest {

	Context ctx = MockContext.createInstance();

	Command setServerCommand = new SetServerCommand();
	Command getCommand = new GetCommand();
	Command pwdCommand = new PwdCommand();
	Command connectCommand = new ConnectCommand();

	private Response result;

	@BeforeClass
	public static void init() throws Exception {
	//	 SkysailServerLauncher.start(new String[0]);
	}

	@AfterClass
	public static void shutdown() throws InterruptedException {
//		SkysailServerLauncher.getRunnable().terminate();
	}

	@Test
	public void startPage_for_unauthorized_user_has_no_links_to_secured_content() throws Exception {
		setServer("http://localhost:2016");
		pwd();
		String um = get().andExpectHeader("Content-Type","application/json; charset=UTF-8").andReturnLink("usermanagement");
		connect();
	}

	private StartAndStopServerTest get() {
		echoCommand("get");
		result = (Response)getCommand.execute(ctx);
		return this;
	}

	private String pwd() {
		echoCommand("pwd");
		return (String)pwdCommand.execute(ctx);
	}

	private void connect() {
		echoCommand("connect");
		connectCommand.execute(ctx);
	}


	private void setServer(String path) {
		echoCommand("setServer", path);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS,path);
		setServerCommand.execute(ctx);
	}
	
	private void writeSeparator(IOConsole console, String string, int length) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < length; j++) {
			sb.append(string);
		}
		sb.append("\n");
		console.writeOutput(sb.toString());
	}

	private StartAndStopServerTest andExpectHeader(String key, String value) {
		assertThat(result.getHeaders().get(key).get(0), is(equalTo(value)));
		return this;
	}

	private String andReturnLink(String title) {
		List<String> links = result.getHeaders().get("Link");
		return parseLinkheaderByTitle(links, title).orElse(null);
	}

	private Optional<String> parseLinkheaderByTitle(List<String> links, String title) {
		return Arrays.stream(links.get(0).split(",")).map(lh -> Linkheader.valueOf(lh)).filter(lh -> lh.getTitle().equals(title)).findFirst().map(lh -> lh.getUri());
	}

	private void echoCommand(String... cmdParts) {
		IOConsole console = ctx.getIoConsole();
		String command = Arrays.stream(cmdParts).collect(Collectors.joining(" "));
		console.writeOutput("\n>>> \"" + command + "\"\n");
		writeSeparator(console, "-", 6+command.length());
	}

}
