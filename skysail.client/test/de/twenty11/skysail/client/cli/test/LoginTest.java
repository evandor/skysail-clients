package de.twenty11.skysail.client.cli.test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;
import org.clamshellcli.test.MockContext;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.ChangePathCommand;
import de.twenty11.skysail.client.cli.commands.ConnectCommand;
import de.twenty11.skysail.client.cli.commands.GetCommand;
import de.twenty11.skysail.client.cli.commands.LoginCommand;
import de.twenty11.skysail.client.cli.commands.PwdCommand;
import de.twenty11.skysail.client.cli.commands.SetServerCommand;

public class LoginTest {

	private Context ctx = MockContext.createInstance();

	private Command setServerCommand = new SetServerCommand();
	private Command getCommand = new GetCommand();
	private Command pwdCommand = new PwdCommand();
	private Command cdCommand = new ChangePathCommand();
	private Command connectCommand = new ConnectCommand();
	private Command loginCommand = new LoginCommand();
	private Command logoutCommand = new LogoutCommand();

	private HttpResponse result;

	@BeforeClass
	public static void init() throws Exception {
		// SkysailServerLauncher.start(new String[0]);
	}

	@AfterClass
	public static void shutdown() throws InterruptedException {
		// SkysailServerLauncher.getRunnable().terminate();
	}

	@Before
	public void setUp() {
		setServer("http://localhost:2016");
		cd(null);
		logout();
	}

	@Test
	public void unauthorized_user_cannot_access_usermanagement()
			throws Exception {
		pwd();
		// String um = get().andReturnLink("usermanagement");
		// String um =
		// get().andExpectHeader("Content-Type","application/json; charset=UTF-8").andReturnLink("usermanagement");
		get();
		cd("usermanagement");
		pwd();
		get().andExpectStatusCode(403);
	}

	@Test
	public void testName() throws Exception {
		pwd();
		login("admin", "skysail");
		cd("usermanagement");
		pwd();
		get().andExpectStatusCode(200);
	}

	private LoginTest get() {
		echoCommand("get");
		result = (HttpResponse) getCommand.execute(ctx);
		return this;
	}

	private String pwd() {
		echoCommand("pwd");
		return (String) pwdCommand.execute(ctx);
	}

	private void cd(String path) {
		echoCommand("cd", path);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, path);
		cdCommand.execute(ctx);
	}

	private void connect() {
		echoCommand("connect");
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, null);
		connectCommand.execute(ctx);
	}

	private void setServer(String path) {
		echoCommand("setServer", path);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, path);
		setServerCommand.execute(ctx);
	}

	private void login(String user, String pass) {
		echoCommand("login", user, "****");
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("username", user);
		arguments.put("password", pass);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, arguments);
		loginCommand.execute(ctx);
	}

	private void logout() {
		echoCommand("logout");
		logoutCommand.execute(ctx);
	}

	private void writeSeparator(IOConsole console, String string, int length) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < length; j++) {
			sb.append(string);
		}
		sb.append("\n");
		console.writeOutput(sb.toString());
	}

	private LoginTest andExpectHeader(String key, String value) {
		// assertThat(result.getHeaders().get(key).get(0), is(equalTo(value)));
		return this;
	}

	private void andExpectStatusCode(int code) {
		int statusCode = result.getStatusLine().getStatusCode();
		assertThat(statusCode, is(code));
	}

	private String andReturnLink(String title) {
		Header[] headers = result.getHeaders("Link");
		String links = headers[0].getValue();
		return parseLinkheaderByTitle(links, title).orElse(null);
	}

	private Optional<String> parseLinkheaderByTitle(String links, String title) {
		return Arrays.stream(links.split(","))
				.map(lh -> Linkheader.valueOf(lh))
				.filter(lh -> lh.getTitle().equals(title)).findFirst()
				.map(lh -> lh.getUri());
	}

	private void echoCommand(String... cmdParts) {
		IOConsole console = ctx.getIoConsole();
		String command = Arrays.stream(cmdParts).collect(
				Collectors.joining(" "));
		console.writeOutput("\n>>> \"" + command + "\"\n");
		writeSeparator(console, "-", 6 + command.length());
	}

}
