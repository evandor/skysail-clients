package de.twenty11.skysail.client.cli.controllers;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;
import org.clamshellcli.core.AnInputController;
import org.clamshellcli.core.ShellException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class NamedParamsController extends AnInputController {

	private static final String JMX_NAMESPACE = "skysail";
	private Map<String, Command> commands;
	private Gson gson;

	@Override
	public boolean handle(Context ctx) {
		String cmdLine = (String) ctx.getValue(Context.KEY_COMMAND_LINE_INPUT);
		boolean handled = false;
		if (cmdLine != null && !cmdLine.trim().isEmpty()) {
			String[] tokens = cmdLine.split("\\s+");
			String cmdName = tokens[0];
			Map<String, Object> argsMap = null;
			// if there are arguments
			if (tokens.length > 1) {
				String argsString = convertParamsToString(Arrays.copyOfRange(
						tokens, 1, tokens.length));
				String argsJson = "{" + argsString + "}";
				try {
					Type mapType = new TypeToken<Map<String, Object>>() {
					}.getType();
					argsMap = gson.fromJson(argsJson, mapType);
				} catch (Exception ex) {
					ctx.getIoConsole().writeOutput(
							String.format(
									"%nUnable to parse command parameters [%s]: "
											+ " %s.%n%n", argsJson,
									ex.getMessage()));
					return true;
				}
			}
			// launch command
			Command cmd = null;
			if (commands != null && (cmd = commands.get(cmdName)) != null) {
				ctx.putValue(Context.KEY_COMMAND_LINE_ARGS, argsMap);
				try {
					cmd.execute(ctx);
				} catch (ShellException se) {
					printException(se, ctx);
				}
				handled = true;
			} else {
				handled = false;
			}
		}
		return handled;
	}

	private void printException(ShellException ex, Context ctx) {
		ctx.getIoConsole().writeOutput(
				String.format("%n%s%n%n", ex.getMessage()));
	}

	private String convertParamsToString(String[] params) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < params.length; i++) {
			buff.append(params[i].trim());
			if (i < (params.length - 1)) {
				buff.append(",");
			}
		}
		return buff.toString();
	}

}
