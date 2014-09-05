package de.twenty11.skysail.client.cli.commands.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.shell.Bootstrap;
import org.springframework.shell.core.CommandResult;
import org.springframework.shell.core.JLineShellComponent;


public class AuthenticationCommandsTest {

private static JLineShellComponent shell;
	
	@BeforeClass
	public static void startUp() throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();		
		shell = bootstrap.getJLineShellComponent();
	}
	
	@AfterClass
	public static void shutdown() {
		shell.stop();
	}

	public static JLineShellComponent getShell() {
		return shell;
	}
	
	@Test
	public void dateTest() throws ParseException {
		
		//Execute command
		CommandResult cr = getShell().executeCommand("login --u admin --p skysail");

		cr.getResult().toString();
		
	}
	
	@Test
	public void dateTest2() throws ParseException {
		
		//Execute command
		CommandResult cr = getShell().executeCommand("date");
		
		//Get result   
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL,Locale.US);
		Date result = df.parse(cr.getResult().toString());
		
		//Make assertions - DateMaters is an external dependency not shown here.
		Date now = new Date();
		//MatcherAssert.assertThat(now, DateMatchers.within(5, TimeUnit.SECONDS, result));		
	}
}
