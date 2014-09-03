package de.twenty11.skysail.client.cli.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.clamshellcli.api.Context;
import org.clamshellcli.api.InputController;
import org.clamshellcli.core.AnInputController;

public class ScriptCommand extends AbstractCommand {

	private static final String SCRIPT_CMD = "script";

	public ScriptCommand() {
		commandDescriptor = new HttpCommandDescriptor(SCRIPT_CMD,
				"script <filename>", "execute script");
	}

	@Override
	public Object doExecute(Context ctx) {

		Optional<AnInputController> inputController = getCmdController(ctx);
		if (!inputController.isPresent()) {
			return null;
		}
		String pathArgument = (String) ctx
				.getValue(Context.KEY_COMMAND_LINE_INPUT);

		String[] split = pathArgument.split(" ");

		Path path = Paths.get(split[1]);
		try (Stream<String> line = Files.lines(path)) {
			line.forEach(l -> run(inputController.get(), ctx, l));
		} catch (IOException e) {

		}

		return null;
	}

	private Optional<AnInputController> getCmdController(Context ctx) {
		List<InputController> controllers = ctx
				.getPluginsByType(InputController.class);
		return controllers.stream().filter(c -> c instanceof AnInputController)
				.map(AnInputController.class::cast).filter(c -> c.getClass().getSimpleName().equals("CmdController")).findFirst();
	}

	private void run(AnInputController inputController, Context context,
			String line) {
		System.out.println("Running: '" + line + "'");
		context.putValue(Context.KEY_COMMAND_LINE_INPUT, line);
		try {
			boolean ctrlResult = inputController.handle(context);
		} catch (Exception ex) {
		}

	}

}
