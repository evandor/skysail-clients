package de.twenty11.skysail.client.cli.test;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;

public class TestBase {

	protected static JLineShellComponent shell;
	
	@BeforeClass
	public static void startUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		shell = bootstrap.getJLineShellComponent();
	}

	@AfterClass
	public static void shutdown() throws Exception {
		shell.stop();
	}

	public static JLineShellComponent getShell() {
		return shell;
	}

	protected CommandResult exec(String... command) {
		return Arrays.stream(command).map(cmd -> getShell().executeCommand(cmd)).reduce((cr1, cr2) -> cr2).get();
	}

}
