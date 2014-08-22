package de.twenty11.skysail.client.cli.testsupport;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.fluent.Form;
import org.apache.http.util.EntityUtils;
import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.api.IOConsole;
import org.clamshellcli.test.MockContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.jayway.jsonpath.JsonPath;

import de.twenty11.skysail.api.responses.Linkheader;
import de.twenty11.skysail.client.cli.commands.ChangePathCommand;
import de.twenty11.skysail.client.cli.commands.ConnectCommand;
import de.twenty11.skysail.client.cli.commands.GetCommand;
import de.twenty11.skysail.client.cli.commands.LoginCommand;
import de.twenty11.skysail.client.cli.commands.LogoutCommand;
import de.twenty11.skysail.client.cli.commands.PostCommand;
import de.twenty11.skysail.client.cli.commands.PwdCommand;
import de.twenty11.skysail.client.cli.commands.SetCommand;
import de.twenty11.skysail.client.cli.commands.SetServerCommand;

public class LargeTestsBase {

	private HttpResponse response;

	@BeforeClass
	public static void init() throws Exception {
		if (runLauncher()) {
			SkysailServerLauncher.start(new String[0]);
		}
	}

	@AfterClass
	public static void shutdown() throws InterruptedException {
		if (runLauncher()) {
			SkysailServerLauncher.getRunnable().terminate();
		}
	}

	private Context ctx = MockContext.createInstance();

	protected Command setServerCommand = new SetServerCommand();
	protected Command getCommand = new GetCommand();
	protected Command pwdCommand = new PwdCommand();
	protected Command cdCommand = new ChangePathCommand();
	protected Command connectCommand = new ConnectCommand();
	protected Command loginCommand = new LoginCommand();
	protected Command logoutCommand = new LogoutCommand();
	protected Command postCommand = new PostCommand();
	protected Command setCommand = new SetCommand();

	protected LargeTestsBase get() {
		echoCommand("get");
		response = (HttpResponse) getCommand.execute(ctx);
		return this;
	}

	protected HttpResponse post(Form form) {
		echoCommand("post", "form to be done");
		Map<String, Object> arguments = new HashMap<>();
		List<NameValuePair> pairs = form.build();
		pairs.stream().forEach(nvp -> {
			arguments.put(nvp.getName(), nvp.getValue());
		});
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, arguments);
		response = (HttpResponse) postCommand.execute(ctx);
		return response;
	}

	protected String pwd() {
		echoCommand("pwd");
		return (String) pwdCommand.execute(ctx);
	}

	protected void cd(String path) {
		echoCommand("cd", path);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, path);
		cdCommand.execute(ctx);
	}

	private void connect() {
		echoCommand("connect");
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, null);
		connectCommand.execute(ctx);
	}

	protected void setServer(String path) {
		echoCommand("setServer", path);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, path);
		setServerCommand.execute(ctx);
	}

	protected void login(String user, String pass) {
		echoCommand("login", user, "****");
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("username", user);
		arguments.put("password", pass);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, arguments);
		loginCommand.execute(ctx);
	}

	protected void logout() {
		echoCommand("logout");
		logoutCommand.execute(ctx);
	}

	protected void set() {
		set("", "");
	}

	protected void set(String param, String value) {
		echoCommand("set", param, value);
		ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, param + " " + value);
		setCommand.execute(ctx);

	}

	private void writeSeparator(IOConsole console, String string, int length) {
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < length; j++) {
			sb.append(string);
		}
		sb.append("\n");
		console.writeOutput(sb.toString());
	}

	private LargeTestsBase andExpectHeader(String key, String value) {
		// assertThat(result.getHeaders().get(key).get(0), is(equalTo(value)));
		return this;
	}

	public void andExpectStatusCode(int code) {
		int statusCode = response.getStatusLine().getStatusCode();
		assertThat(statusCode, is(code));
	}

	private String andReturnLink(String title) {
		Header[] headers = response.getHeaders("Link");
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

    public String extractFromBody(HttpResponse response, String jsonPath) {
        try {
            String json = EntityUtils.toString(response.getEntity());
            return JsonPath.read(json, jsonPath);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }



	protected int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private static boolean runLauncher() {
		String runLauncher = System.getProperty("runLauncher");
		if (runLauncher == null || runLauncher.trim().length() == 0) {
			return false;
		}
		return Boolean.valueOf(runLauncher);
	}


}
