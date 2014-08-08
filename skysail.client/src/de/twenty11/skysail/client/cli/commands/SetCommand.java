package de.twenty11.skysail.client.cli.commands;

import java.util.stream.Collectors;

import org.clamshellcli.api.Command;
import org.clamshellcli.api.Context;

import de.twenty11.skysail.client.cli.commands.utils.CtxUtils;

public class SetCommand extends AbstractCommand {

	public static final String CMD_NAME = "list";
	public static final String NAMESPACE = "jmx";
	public static final String KEY_ARGS_FILTER = "filter";
	public static final String KEY_ARGS_LABEL = "label";

	public Command.Descriptor descriptor = null;
	
	@Override
	public Object execute(Context ctx) {
		String pathArgument = (String)ctx.getValue(Context.KEY_COMMAND_LINE_ARGS);
		if (noArgumentGiven(pathArgument)) {
			printAllCtxValues(ctx);
			return null;
		}
		String[] split = pathArgument.split(" ");
		if (split.length != 2) {
			throw new IllegalArgumentException();
		}
		CtxUtils.set(ctx, split[0], split[1]);
		return null;
	}

	private void printAllCtxValues(Context ctx) {
		ctx.getIoConsole().writeOutput(ctx.getValues().keySet().stream().map(k -> {
			return k + ": " + ctx.getValue(k);
		}).collect(Collectors.joining("\n")));
	}

	private boolean noArgumentGiven(String pathArgument) {
		return pathArgument == null || pathArgument.trim().length() == 0;
	}

}
